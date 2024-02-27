package who.is.neighbor.citizen.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.account.infrastructure.jpa.AccountEntity;

import java.util.List;

public interface CitizenJpaRepository extends JpaRepository<CitizenEntity, Long> {
    List<CitizenEntity> findByCitizenId(Long citizenId);

    CitizenEntity findByAccountEntity(AccountEntity account);
}
