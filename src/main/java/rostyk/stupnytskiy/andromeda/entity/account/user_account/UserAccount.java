package rostyk.stupnytskiy.andromeda.entity.account.user_account;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.cart.Cart;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.account.UserRole;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor

@Entity
public class UserAccount extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    public UserAccount() {
        super.setUserRole(UserRole.ROLE_USER);
    }
}
