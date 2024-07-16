package who.is.neighbor.citizen.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import who.is.neighbor.account.infrastructure.AccountRepository;
import who.is.neighbor.account.infrastructure.entity.AccountEntity;
import who.is.neighbor.address.domain.Address;
import who.is.neighbor.address.infrastructure.entity.AddressEntity;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.citizen.infrastructure.CitizenJpaRepository;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;
import who.is.neighbor.citizen.infrastructure.entity.CitizenHobbyEntity;
import who.is.neighbor.citizen.web.request.CitizenRegistrationRequest;
import who.is.neighbor.citizen.web.response.CitizenResponse;
import who.is.neighbor.hobby.domain.Hobby;
import who.is.neighbor.hobby.infrastructure.HobbyJpaRepository;
import who.is.neighbor.hobby.infrastructure.entity.HobbyEntity;
import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;
import who.is.neighbor.hobby.web.response.HobbyResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitizenService {

    private final CitizenJpaRepository citizenRepository;
    private final AccountRepository accountRepository;
    private final HobbyJpaRepository hobbyJpaRepository;

    public List<Citizen> findCitizenByAccountId(Long citizenId) {
        CitizenEntity citizenEntity = citizenRepository.findById(citizenId).orElseThrow();
        Citizen citizen = citizenEntity.toDomain();
        return List.of(citizen);
    }

    public HttpStatus delete(Long accountId) {
        AccountEntity accountEntity = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        CitizenEntity citizenEntity = citizenRepository.findByAccountEntity(accountEntity);
        citizenRepository.delete(citizenEntity);
        return HttpStatus.OK;
    }

    @Transactional
    public CitizenResponse save(UserDetails userDetails, CitizenRegistrationRequest request) {
        String username = userDetails.getUsername();
        AccountEntity accountEntity = accountRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!accountEntity.isEmailVerified()){
            throw new IllegalArgumentException("이메일 인증이 되지 않은 사용자는 시민 등록을 할 수 없습니다.");
        }

        Citizen citizen = new Citizen(request.nickname()
                , LocalDate.now()
                , request.phoneNumber()
                , null
                , null
        );

        CitizenEntity citizenEntity = citizenRepository.save(new CitizenEntity(citizen));

        Optional.ofNullable(request.hobbyRegistrationRequest()).ifPresent(hobbyRegistrationRequest -> {
            Hobby hobby = Hobby.from(hobbyRegistrationRequest.hobby());
            HobbyEntity hobbyEntity = hobbyJpaRepository.save(new HobbyEntity(hobby));
            citizenEntity.addHobby(hobbyEntity);
        });

        Optional.ofNullable(request.addressRegistrationRequest()).ifPresent(addressRegistrationRequest -> {
            Address address = Address.from(addressRegistrationRequest);
            AddressEntity addressEntity = new AddressEntity(address);
            citizenEntity.addAddress(addressEntity);
        });

        List<HobbyResponse> hobbyResponses = citizenEntity.getHobbyEntities().stream()
                .map(citizenHobbyEntity -> citizenHobbyEntity.getHobby().toDomain())
                .map(hobby -> new HobbyResponse(hobby.id(), hobby.hobby()))
                .toList();

        List<AddressResponse> addressResponses = citizenEntity.getAddressEntities().stream()
                .map(AddressEntity::toDomain)
                .map(AddressResponse::from)
                .toList();

        return new CitizenResponse(citizenEntity.getCitizenId()
                , citizenEntity.getNickname()
                , citizenEntity.getPhoneNumber()
                , hobbyResponses
                , addressResponses
        );
    }

    public void addHobbyToCitizen(long id, HobbyRegistrationRequest request) {
        CitizenEntity citizenEntity = citizenRepository.findById(id).orElseThrow();
        Hobby hobby = Hobby.from(request.hobby());
        HobbyEntity hobbyEntity = hobbyJpaRepository.save(new HobbyEntity(hobby));
        citizenEntity.addHobby(hobbyEntity);
    }

    public List<HobbyEntity> findHobbiesByCitizenId(long id) {
        CitizenEntity citizenEntity = citizenRepository.findById(id).orElseThrow();
        return citizenEntity.getHobbyEntities().stream().map(CitizenHobbyEntity::getHobby).collect(Collectors.toList());
    }
}
