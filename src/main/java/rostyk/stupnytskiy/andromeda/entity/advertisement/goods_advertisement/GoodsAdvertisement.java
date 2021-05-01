package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.Subcategory;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.categories.GoodsSellerAdvertisementCategory;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.discount.Discount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.Parameter;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters.ParametersValuesPriceCount;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@DiscriminatorValue("goods_advertisement")
public class GoodsAdvertisement extends Advertisement {

    private Boolean onlySellerCountry;

    private Double priceToSort;

    private Boolean hasParameters;

    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<Parameter> parameters;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<ParametersValuesPriceCount> valuesPriceCounts;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subcategory subcategory;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsSellerAccount seller;

    @OneToMany(mappedBy = "advertisement", fetch = FetchType.LAZY)
    private List<Property> properties = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<DeliveryType> deliveryTypes = new ArrayList<>();

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<GoodsAdvertisementFeedback> feedbacks;

    @ManyToMany(mappedBy = "favoriteAdvertisements", fetch = FetchType.LAZY)
    private List<UserAccount> users;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<Discount> discounts;

    @ManyToOne
    private GoodsSellerAdvertisementCategory sellerCategory;

    @OneToMany(mappedBy = "goodsAdvertisement", fetch = FetchType.LAZY)
    private List<GoodsOrderItem> orderItems = new ArrayList<>();

    @Override
    public String toString() {
        return "GoodsAdvertisement{ id = " + this.getId() + ", seller = " + this.getSeller().toString() + "}";
    }

    public Double getMaxPrice() {
        return valuesPriceCounts
                .stream()
                .mapToDouble(ParametersValuesPriceCount::getPrice)
                .max()
                .getAsDouble();
    }

    public Double getMinPrice() {
        return valuesPriceCounts
                .stream()
                .mapToDouble(ParametersValuesPriceCount::getPrice)
                .min()
                .getAsDouble();
    }

    public Double getMaxPriceWithDiscounts() {
        return valuesPriceCounts
                .stream()
                .mapToDouble(ParametersValuesPriceCount::getPriceWithCurrentDiscount)
                .max()
                .getAsDouble();
    }

    public Double getMinPriceWithDiscounts() {
        return valuesPriceCounts
                .stream()
                .mapToDouble(ParametersValuesPriceCount::getPriceWithCurrentDiscount)
                .min()
                .getAsDouble();
    }

    public boolean hasDiscount() {
        for (ParametersValuesPriceCount valuesPriceCount : valuesPriceCounts)
            if (valuesPriceCount.hasDiscount()) return true;
        return false;
    }

    public int getCountOrdersByYearAndMonth(int year, Month month) {
        int count = 0;
        for (GoodsOrderItem orderItem : orderItems) {
            if (orderItem.getGoodsOrder().getCreationDate().getMonth() == month &&
                    orderItem.getGoodsOrder().getCreationDate().getYear() == year) count++;
        }
        return count;
    }

}

