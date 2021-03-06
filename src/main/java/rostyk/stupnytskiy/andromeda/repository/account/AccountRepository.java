package rostyk.stupnytskiy.andromeda.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.andromeda.entity.account.Account;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<? extends Account> findByLogin(String login);
    boolean existsByLogin(String login);
}
