package who.is.neighbor.citizen.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CitizenJpaRepository extends JpaRepository<CitizenEntity, Long> {
    List<CitizenEntity> findByCitizenId(Long citizenId);

    Optional<CitizenEntity> findByAccountId(Long accountId);
}
