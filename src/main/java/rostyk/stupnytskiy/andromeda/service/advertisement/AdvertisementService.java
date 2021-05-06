package rostyk.stupnytskiy.andromeda.service.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementSearchRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.repository.advertisement.AdvertisementRepository;
import rostyk.stupnytskiy.andromeda.repository.advertisement.goods_advertisement.GoodsAdvertisementRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.SubcategoryService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.specification.GoodsAdvertisementSpecification;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private GoodsAdvertisementRepository goodsAdvertisementRepository;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    public Advertisement findById(Long id) {
        return advertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No advertisement with id " + id));
    }

    public GoodsAdvertisement findGoodsAdvertisementById(Long id) {
        return goodsAdvertisementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No advertisement with id " + id));
    }


    public List<AdvertisementResponse> findAll() {
        goodsAdvertisementRepository.findAll().forEach((a) -> System.out.println(a.getClass().getName()));
        return goodsAdvertisementRepository.findAll()
                .stream()
                .map(GoodsAdvertisementResponse::new)
                .collect(Collectors.toList());
    }

    public PageResponse<GoodsAdvertisementForSearchResponse> findPageBySearchRequest(GoodsAdvertisementSearchRequest request) {
        final Page<GoodsAdvertisement> page = goodsAdvertisementRepository.findAll(
                new GoodsAdvertisementSpecification(request),
                request.getPaginationRequest().mapToPageable()
        );

        request.setCurrencyCode(currencyService.auditUserCurrency(request.getCurrencyCode()));
        return null;

//        return new PageResponse<>(page.get()
//                .map(GoodsAdvertisementForSearchResponse::new)
//                .peek((r) -> {
//                    r.setMinPrice(currencyService.exchangeCurrencyFromDollar(r.getMinPrice(), request.getCurrencyCode()));
//                    r.setMaxPrice(currencyService.exchangeCurrencyFromDollar(r.getMaxPrice(), request.getCurrencyCode()));
//                    r.setCurrencyCode(request.getCurrencyCode());
//                })
//                .collect(Collectors.toList()),
//                page.getTotalElements(),
//                page.getTotalPages());
    }

}
