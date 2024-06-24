package who.is.neighbor.citizen.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;

public interface CitizenRepository extends JpaRepository<CitizenEntity, Long>{

    @Query("SELECT c FROM CitizenEntity c JOIN c.accountEntity a WHERE a.accountId = :accountId")
    CitizenEntity findByAccountId(Long accountId);

}
