package rostyk.stupnytskiy.andromeda.entity;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.country.Country;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class DeliveryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;  // назва поштової служби чи способу доставки

    private Boolean international; // визначає чи доставка лише у межах власної країни чи ні

    @ManyToOne
    private Country country;

    @ManyToMany(mappedBy = "deliveryTypes")
    private List<GoodsAdvertisement> goodsAdvertisements;

    @ManyToMany(mappedBy = "deliveryTypes")
    private Set<GoodsSellerAccount> sellerList;
}
