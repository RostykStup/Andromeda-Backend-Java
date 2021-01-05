package rostyk.stupnytskiy.andromeda.service.account.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.GoodsSellerDataRequest;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.repository.GoodsSellerRepository;
import rostyk.stupnytskiy.andromeda.service.account.AccountService;
import rostyk.stupnytskiy.andromeda.service.CountryService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;

@Service
public class GoodsSellerAccountService {

    @Autowired
    private GoodsSellerRepository goodsSellerRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    // save the main goods seller data
    public void updateGoodsSellerData(GoodsSellerDataRequest request) {
        GoodsSellerAccount goodsSellerAccount = findBySecurityContextHolder();
        goodsSellerAccount.setOnlySellerCountryDelivery(request.getOnlySellerCountryDelivery());

        if (request.getDeliveryTypesId() != null)
            goodsSellerAccount.setDeliveryTypes(deliveryTypeService.getDeliverySetByIds(request.getDeliveryTypesId()));
        goodsSellerRepository.save(goodsSellerAccount);
    }


    public GoodsSellerAccount findBySecurityContextHolder() {
        return findById(accountService.getIdBySecurityContextHolder());
    }

    public GoodsSellerAccount findById(Long id) {
        return goodsSellerRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }
}
