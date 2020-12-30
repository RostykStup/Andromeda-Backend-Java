package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderItemRequest;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItemStatus;
import rostyk.stupnytskiy.andromeda.repository.GoodsOrderItemRepository;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;

@Service
public class GoodsOrderItemService {

    @Autowired
    private GoodsOrderItemRepository goodsOrderItemRepository;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    public void save(GoodsOrderItemRequest request, GoodsOrder goodsOrder){
        goodsOrderItemRepository.save(goodsOrderItemRequestToGoodsOrderItem(request, goodsOrder));
    }

    public void confirmGoodsOrderItemSending(GoodsOrderItem orderItem){
        orderItem.setStatus(GoodsOrderItemStatus.WAITING_FOR_DELIVERY);
        goodsOrderItemRepository.save(orderItem);
    }

    public void confirmGoodsOrderItemDelivery(GoodsOrderItem orderItem){
        orderItem.setStatus(GoodsOrderItemStatus.WAITING_FOR_FEEDBACK);
        goodsOrderItemRepository.save(orderItem);
    }

    public GoodsOrderItem goodsOrderItemRequestToGoodsOrderItem(GoodsOrderItemRequest request, GoodsOrder goodsOrder){
        GoodsOrderItem goodsOrderItem = new GoodsOrderItem();
        goodsOrderItem.setCount(request.getCount());
        goodsOrderItem.setDescription(request.getDescription());
        goodsOrderItem.setStatus(GoodsOrderItemStatus.WAITING_FOR_SHIPMENT);
        goodsOrderItem.setGoodsAdvertisement(goodsAdvertisementService.findById(request.getGoodsAdvertisementId()));
        goodsOrderItem.setGoodsOrder(goodsOrder);
        return goodsOrderItem;
    }

    public GoodsOrderItem findById(Long id){
        return goodsOrderItemRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsOrderItem findByIdAndUser(Long id, UserAccount user){
        return goodsOrderItemRepository.findByIdAndGoodsOrderUser(id, user).orElseThrow(IllegalArgumentException::new);
    }

}