package who.is.neighbor.citizen.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import who.is.neighbor.account.infrastructure.jpa.AccountEntity;
import who.is.neighbor.account.infrastructure.jpa.AccountJpaRepository;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.citizen.domain.CitizenRepository;
import who.is.neighbor.citizen.infrastructure.jpa.CitizenEntity;
import who.is.neighbor.citizen.infrastructure.jpa.CitizenJpaRepository;
import who.is.neighbor.citizen.web.response.CitizenResponse;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CitizenDao implements CitizenRepository {
    private final CitizenJpaRepository citizenJpaRepository;
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public CitizenResponse save(Citizen citizen) {
        CitizenEntity citizenEntity = citizenJpaRepository.save(new CitizenEntity(citizen));
        return new CitizenResponse(citizenEntity.toDomain());
    }

    @Override
    public List<Citizen> findByCitizenId(Long citizenId) {
        List<CitizenEntity> citizenEntityList = citizenJpaRepository.findByCitizenId(citizenId);
        return citizenEntityList.stream().map(CitizenEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Citizen findByAccountId(Long accountId) {
        return null;
    }

    @Override
    public Citizen delete(Long accountId) {
        AccountEntity account = accountJpaRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));
        CitizenEntity citizenEntity = citizenJpaRepository.findByAccountEntity(account);
        citizenEntity.delete();
        return citizenEntity.toDomain();
    }
}
