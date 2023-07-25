package who.is.neighbor.citizen.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import who.is.neighbor.citizen.domain.CitizenHobby;
import who.is.neighbor.citizen.domain.CitizenHobbyRepository;
import who.is.neighbor.citizen.infrastructure.jpa.CitizenHobbyEntity;
import who.is.neighbor.citizen.infrastructure.jpa.CitizenHobbyJpaRepository;

@Repository
@RequiredArgsConstructor
public class CitizenHobbyDao implements CitizenHobbyRepository {
    private final CitizenHobbyJpaRepository citizenHobbyJpaRepository;

    @Override
    public CitizenHobbyEntity save(CitizenHobby citizenHobby) {
        CitizenHobbyEntity citizenHobbyEntity = new CitizenHobbyEntity(citizenHobby.citizen(), citizenHobby.hobby());
        return citizenHobbyJpaRepository.save(citizenHobbyEntity);
    }
}
