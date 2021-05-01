package rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller;

import lombok.*;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.GoodsSellerResponse;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.SellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.categories.GoodsSellerAdvertisementCategory;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsSellerFeedback;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class GoodsSellerAccount extends SellerAccount {

    private Boolean onlySellerCountryDelivery = false;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<GoodsAdvertisement> advertisements;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<DeliveryType> deliveryTypes;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<GoodsSellerFeedback> feedbacks;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<GoodsOrder> goodsOrders;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private GoodsSellerSettings settings;

    @OneToMany(mappedBy = "goodsSeller")
    private List<GoodsSellerAdvertisementCategory> categories;

    @Override
    public String toString() {
        return "GoodsSellerAccount{" +
                "Name:" + this.getShopName() +
                ", id:" + this.getId() +
                '}';
    }

    public GoodsSellerResponse mapToResponse() {
        return new GoodsSellerResponse(this);
    }
}
