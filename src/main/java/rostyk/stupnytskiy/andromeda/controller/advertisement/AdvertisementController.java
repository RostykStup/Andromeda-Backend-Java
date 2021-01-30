package rostyk.stupnytskiy.andromeda.controller.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.PropertyRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.retail.RetailGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.GoodsAdvertisementSearchRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisementRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.AdvertisementResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementForSearchResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.GoodsAdvertisementStatisticsResponse;
import rostyk.stupnytskiy.andromeda.service.advertisement.AdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.statistics.advertisement.GoodsAdvertisementStatisticsService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    @Autowired
    private GoodsAdvertisementStatisticsService goodsAdvertisementStatisticsService;

    @GetMapping
    private <T extends AdvertisementResponse> AdvertisementResponse findOneById(Long id) {
        return advertisementService.findById(id).mapToResponse();
    }

    @GetMapping("editing")
    private <T extends AdvertisementResponse> AdvertisementResponse findForEditing(Long id) {
        return goodsAdvertisementService.findByIdAndSeller(id).mapToResponse();
    }

    @GetMapping("seller")
    private PageResponse<AdvertisementResponse> getAllSellerAdvertisementsPage(Long id, PaginationRequest request) {
        return goodsAdvertisementService.findAllSellerAdvertisementsPage(id, request);
    }

    @PostMapping("retail")
    private void createRetail(@Valid @RequestBody RetailGoodsAdvertisementRequest request) {
        goodsAdvertisementService.saveRetailGoodsAdvertisement(request);
    }

    @PostMapping("/wholesale")
    public void createWholesale(@Valid @RequestBody WholesaleGoodsAdvertisementRequest request) {
        goodsAdvertisementService.saveWholesaleGoodsAdvertisement(request);
    }

    @GetMapping("/filter")
    public PageResponse<GoodsAdvertisementForSearchResponse> findForSearch(GoodsAdvertisementSearchRequest request) {
        return advertisementService.findPageBySearchRequest(request); //(request);
    }

    @GetMapping("/count")
    private Integer getAdvertisementCount(Long id) {
        return goodsAdvertisementService.findById(id).getCount();
    }

//    @PutMapping
//    public void test() {
//        goodsAdvertisementService.exchangePriceForAll();
//    }

    @PutMapping("change-title")
    public void changeGoodsAdvertisementTitle(Long id, @RequestBody String title) {
        goodsAdvertisementService.changeAdvertisementTitle(id, title);
    }

    @PutMapping("change-count")
    public void changeGoodsAdvertisementCount(Long id, @RequestBody Integer count) {
        goodsAdvertisementService.changeAdvertisementCount(id, count);
    }

    @PutMapping("change-description")
    public void changeGoodsAdvertisementDescription(Long id, @RequestBody(required = false) String description) {
        goodsAdvertisementService.changeAdvertisementDescription(id, description);
    }

    @PutMapping("change-properties")
    public void changeGoodsAdvertisementProperties(Long id, @RequestBody List<PropertyRequest> properties) {
        goodsAdvertisementService.changeAdvertisementProperties(id, properties);
    }

    @PutMapping("change-deliveries")
    public void changeGoodsAdvertisementDeliveries(Long id, @RequestBody List<Long> deliveryIds) {
        goodsAdvertisementService.changeAdvertisementDeliveries(id, deliveryIds);
    }

    @PutMapping("change-sellerDelivery")
    public void changeGoodsAdvertisementOnlySeller(Long id, Boolean isOnly) {
        goodsAdvertisementService.changeAdvertisementOnlySellerCountry(id, isOnly);
    }

    @PutMapping("add-image")
    public String addImageToGoodsAdvertisement(Long id, @RequestBody String image) {
        return goodsAdvertisementService.addImageToGoodsAdvertisement(id, image);
    }

    @PutMapping("delete-image")
    public void deleteGoodsAdvertisementImage(Long id, String image) {
        goodsAdvertisementService.deleteGoodsAdvertisementImage(id, image);
    }

    @PutMapping("main-image")
    public void makeMainImageGoodsAdvertisement(Long id, String image) {
        goodsAdvertisementService.makeMainImageToGoodsAdvertisement(id, image);
    }

    @GetMapping("statistics")
    public GoodsAdvertisementStatisticsResponse getGoodsAdvertisementStatistics(Long id){
        return new GoodsAdvertisementStatisticsResponse(goodsAdvertisementStatisticsService.findOneByGoodsAdvertisementId(id));
    }

    @PutMapping("add-to-favorites")
    public void addToFavorites(Long id) {
        goodsAdvertisementService.addToFavorites(id);
    }

    @PutMapping("remove-from-favorites")
    public void removeFromFavorites(Long id) {
        goodsAdvertisementService.removeFromFavorites(id);
    }

    @GetMapping("is-in-favorites")
    public Boolean isInFavorites(Long id) {
        return goodsAdvertisementService.isInFavorites(id);
    }



//    @PutMapping
//    private void addStatistics() {
//        advertisementService.addStatisticsToAll();
//    }


//    @PostMapping
//    private void create(@Valid @RequestBody AdvertisementCreationRequest request){
//        advertisementService.createAdvertisement(request);
//    }
//
//    @PutMapping("/change-price")
//    private void changeAdvertisementPrice(@Valid @RequestBody AdvertisementChangePriceRequest request, Long id){
//        advertisementService.changeAdvertisementPrice(request, id);
//    }

//    @GetMapping
//    private AdvertisementResponse test(Long id){
//        return new AdvertisementResponse(advertisementService.findById(id));
//    }
}
