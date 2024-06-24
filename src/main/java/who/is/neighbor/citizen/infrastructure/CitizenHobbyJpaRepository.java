package who.is.neighbor.citizen.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.citizen.infrastructure.entity.CitizenHobbyEntity;
import who.is.neighbor.citizen.infrastructure.entity.CitizenHobbyId;

public interface CitizenHobbyJpaRepository extends JpaRepository<CitizenHobbyEntity, CitizenHobbyId> {
}
