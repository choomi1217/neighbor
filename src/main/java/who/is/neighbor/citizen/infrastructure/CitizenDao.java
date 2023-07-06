package who.is.neighbor.citizen.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.citizen.domain.CitizenRepository;
import who.is.neighbor.citizen.infrastructure.jpa.CitizenEntity;
import who.is.neighbor.citizen.infrastructure.jpa.CitizenJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CitizenDao implements CitizenRepository {
    private final CitizenJpaRepository userJpaRepository;

    @Override
    public Citizen save(Citizen user) {
        CitizenEntity citizenEntity = userJpaRepository.save(new CitizenEntity(user));
        return citizenEntity.toDomain();
    }

    @Override
    public List<Citizen> findByCitizenId(Long citizenId) {
        List<CitizenEntity> citizenEntityList = userJpaRepository.findByCitizenId(citizenId);
        return citizenEntityList.stream().map(CitizenEntity::toDomain).collect(Collectors.toList());
    }
}
