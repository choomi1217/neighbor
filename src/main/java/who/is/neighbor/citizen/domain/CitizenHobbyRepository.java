package who.is.neighbor.citizen.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.citizen.infrastructure.entity.CitizenHobbyEntity;

public interface CitizenHobbyRepository extends JpaRepository<CitizenHobbyEntity, Long> {
    CitizenHobbyEntity save(CitizenHobby citizenHobby);
}
