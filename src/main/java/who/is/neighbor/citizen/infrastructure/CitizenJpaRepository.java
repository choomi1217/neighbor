package who.is.neighbor.citizen.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.account.infrastructure.entity.AccountEntity;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;

import java.util.List;

public interface CitizenJpaRepository extends JpaRepository<CitizenEntity, Long> {

    CitizenEntity findByAccountEntity(AccountEntity accountEntity);

    CitizenEntity findByCitizenId(Long citizenId);
}
