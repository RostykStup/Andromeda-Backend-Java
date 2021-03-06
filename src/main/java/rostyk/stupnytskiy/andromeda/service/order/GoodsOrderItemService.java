package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderItemRequest;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItemStatus;
import rostyk.stupnytskiy.andromeda.repository.order.goods_order.GoodsOrderItemRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.parameter.ParameterService;
import rostyk.stupnytskiy.andromeda.service.cart.CartService;

@Service
public class GoodsOrderItemService {

    @Autowired
    private GoodsOrderItemRepository goodsOrderItemRepository;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CartService cartService;


    public void save(GoodsOrderItemRequest request, GoodsOrder goodsOrder) {
        goodsOrderItemRepository.save(goodsOrderItemRequestToGoodsOrderItem(request, goodsOrder));
        parameterService.minusParamsValuesCount(request.getParamsValuesId(), request.getCount());
        cartService.deleteGoodsItemFromCart(request.getCartItemId());
    }

    public void confirmGoodsOrderItemSending(GoodsOrderItem orderItem) {
        orderItem.setStatus(GoodsOrderItemStatus.WAITING_FOR_DELIVERY);
        goodsOrderItemRepository.save(orderItem);
    }

    public void confirmGoodsOrderItemDelivery(GoodsOrderItem orderItem) {
        orderItem.setStatus(GoodsOrderItemStatus.WAITING_FOR_FEEDBACK);
        goodsOrderItemRepository.save(orderItem);
    }

    public GoodsOrderItem goodsOrderItemRequestToGoodsOrderItem(GoodsOrderItemRequest request, GoodsOrder goodsOrder) {
        GoodsOrderItem goodsOrderItem = new GoodsOrderItem();
        goodsOrderItem.setCount(request.getCount());
        goodsOrderItem.setDescriptionFromUser(request.getDescription());
        goodsOrderItem.setStatus(GoodsOrderItemStatus.WAITING_FOR_SENDING);
        goodsOrderItem.setGoodsAdvertisement(goodsAdvertisementService.findById(request.getGoodsAdvertisementId()));
        goodsOrderItem.setParametersValuesPriceCount(parameterService.findParametersValuesPriceCountById(request.getParamsValuesId()));
        goodsOrderItem.setPrice(request.getPrice());
        goodsOrderItem.setGoodsOrder(goodsOrder);
        return goodsOrderItem;
    }

    public GoodsOrderItem findById(Long id) {
        return goodsOrderItemRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsOrderItem findByIdAndUser(Long id, UserAccount user) {
        return goodsOrderItemRepository.findByIdAndGoodsOrderUser(id, user).orElseThrow(IllegalArgumentException::new);
    }

    public Double exchangeItemPrice(Long id, String currency) {
        GoodsOrderItem item = findById(id);
        if (item.getParametersValuesPriceCount().hasCurrency(currency)) {
            return item.getParametersValuesPriceCount().getPriceByCurrency(currency);
        } else {
            return currencyService.exchangeCurrencyFromDollar(item.getParametersValuesPriceCount().getPriceByCurrency("USD"), currency);
        }
    }

    public void exchangeItemPriceAndSave(GoodsOrderItem i, String currency) {
        i.setPrice(exchangeItemPrice(i.getId(), currency));
        goodsOrderItemRepository.save(i);
    }
}
