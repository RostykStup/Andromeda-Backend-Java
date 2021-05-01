package rostyk.stupnytskiy.andromeda.entity.account.user_account;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.cart.Cart;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.account.UserRole;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.user.UserAdvertisementView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor

@Entity
public class UserAccount extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Cart cart;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<GoodsOrder> goodsOrders = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private UserDeliveryAddress defaultAddress;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<UserDeliveryAddress> addresses = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<GoodsAdvertisement> favoriteAdvertisements = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private UserSettings settings;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<UserAdvertisementView> views;

    public UserAccount() {
        super.setUserRole(UserRole.ROLE_USER);
    }


}
