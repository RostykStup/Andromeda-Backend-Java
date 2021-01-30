package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.wholesale;

import com.sun.nio.sctp.IllegalReceiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesalePriceRequest;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePrice;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.WholesaleGoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.PropertyService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

@Service
public class WholesaleGoodsAdvertisementService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private WholesaleGoodsAdvertisementRepository wholesaleGoodsAdvertisementRepository;

    @Autowired
    private WholesalePriceService wholesalePriceService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private FileTool fileTool;


    public WholesalePrice finishAdvertisementCreation(WholesaleGoodsAdvertisement advertisement, WholesaleGoodsAdvertisementRequest request) {
        if (request.getProperties() != null)
            request.getProperties().forEach(p -> propertyService.save(p, advertisement));
        return wholesalePriceService.save(request.getPrice(),advertisement);
    }

    public void addNewWholesalePriceToWholesaleGoodsAdvertisement(WholesalePriceRequest request, Long id) throws IllegalAccessException {
        wholesalePriceService.validWholesaleUnit(request);
        WholesaleGoodsAdvertisement advertisement = findById(id);
        if (goodsSellerAccountService.findBySecurityContextHolder() == advertisement.getSeller()) {
            advertisement.setPriceToSort(wholesalePriceService.save(request,advertisement).getMinPrice());
            advertisementRepository.save(advertisement);
        }
        else throw new IllegalAccessException();

//        wholesalePriceService.save(request,findById(id));
    }

    public WholesaleGoodsAdvertisement findById(Long id){
        return wholesaleGoodsAdvertisementRepository.findById(id).orElseThrow(IllegalReceiveException::new);
    }

    public void validWholesaleUnit(WholesalePriceRequest request) {
        wholesalePriceService.validWholesaleUnit(request);
    }
}
