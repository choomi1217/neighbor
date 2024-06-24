package who.is.neighbor.account.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.account.infrastructure.entity.AccountEntity;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByEmail(String email);

    Optional<AccountEntity> findByAccountId(Long accountId);
}
