package rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.parameter.ParameterResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.parameter.ParametersValuesPriceCountResponse;
import rostyk.stupnytskiy.andromeda.dto.response.category.SubcategoryResponse;
import rostyk.stupnytskiy.andromeda.dto.response.country.CurrencyResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GoodsAdvertisementResponse extends AdvertisementResponse {

    private Boolean onlySellerCountry;

    private SubcategoryResponse subcategory;

    private List<String> images;

    private List<PropertyResponse> properties;

    private List<ParameterResponse> parameters;

    private List<ParametersValuesPriceCountResponse> valuesPriceCounts;

    private Integer totalCount;

    private String seller;

    private Long sellerId;

    private String countryCode;

    private Boolean hasParameters;

    private Double minPrice;
    private Double maxPrice;


    public GoodsAdvertisementResponse(GoodsAdvertisement advertisement) {
        super(advertisement);
        this.onlySellerCountry = advertisement.getOnlySellerCountry();
        this.subcategory = new SubcategoryResponse(advertisement.getSubcategory());
        this.images = advertisement.getImages();
        this.properties = advertisement.getProperties().stream().map(PropertyResponse::new).collect(Collectors.toList());
        this.seller = advertisement.getSeller().getShopName();
        this.sellerId = advertisement.getSeller().getId();
        this.hasParameters = advertisement.getHasParameters();
        this.countryCode = advertisement.getSeller().getCountry().getCountryCode();

        this.totalCount = advertisement.getValuesPriceCounts().stream().mapToInt(ParametersValuesPriceCount::getCount).sum();

        if (advertisement.getHasParameters()) {
            this.maxPrice = advertisement.getValuesPriceCounts()
                    .stream()
                    .mapToDouble(ParametersValuesPriceCount::getPrice)
                    .max()
                    .getAsDouble();

            this.minPrice = advertisement.getValuesPriceCounts()
                    .stream()
                    .mapToDouble(ParametersValuesPriceCount::getPrice)
                    .min()
                    .getAsDouble();
        } else {
            this.maxPrice = advertisement.getValuesPriceCounts().get(0).getPrice();
            this.minPrice = advertisement.getValuesPriceCounts().get(0).getPrice();
        }

        this.parameters = advertisement.getParameters().stream().map(ParameterResponse::new).collect(Collectors.toList());
        this.valuesPriceCounts = advertisement.getValuesPriceCounts().stream().map(ParametersValuesPriceCountResponse::new).collect(Collectors.toList());
    }
}
