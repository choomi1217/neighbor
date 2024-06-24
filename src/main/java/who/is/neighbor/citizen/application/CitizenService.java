package who.is.neighbor.citizen.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import who.is.neighbor.account.domain.Account;
import who.is.neighbor.account.infrastructure.entity.AccountEntity;
import who.is.neighbor.account.infrastructure.AccountRepository;
import who.is.neighbor.address.domain.Address;
import who.is.neighbor.address.infrastructure.AddressRepository;
import who.is.neighbor.address.infrastructure.entity.AddressEntity;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.citizen.domain.CitizenHobby;
import who.is.neighbor.citizen.domain.CitizenHobbyRepository;
import who.is.neighbor.citizen.domain.CitizenRepository;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;
import who.is.neighbor.citizen.web.request.CitizenRegistrationRequest;
import who.is.neighbor.citizen.web.response.CitizenRegistrationResponse;
import who.is.neighbor.citizen.web.response.CitizenResponse;
import who.is.neighbor.hobby.domain.Hobby;
import who.is.neighbor.hobby.domain.HobbyRepository;
import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;
import who.is.neighbor.hobby.web.response.HobbyResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CitizenService {

    private final AddressRepository addressRepository;
    private final CitizenRepository citizenRepository;
    private final HobbyRepository hobbyRepository;
    private final CitizenHobbyRepository citizenHobbyRepository;
    private final AccountRepository accountRepository;

    public List<Citizen> findUserByAccountId(Long citizenId) {
        CitizenEntity citizenEntity = citizenRepository.findById(citizenId).orElseThrow();
        Citizen citizen = citizenEntity.toDomain();
        return List.of(citizen);
    }

    public HttpStatus delete(Long accountId) {
        AccountEntity accountEntity = accountRepository.findByAccountId(accountId).orElseThrow();
        Account account = accountEntity.toDomain();
        CitizenEntity citizenEntity = citizenRepository.findByAccountId(account.accountId());
        Citizen citizen = citizenEntity.toDomain();
        citizenRepository.delete(new CitizenEntity(citizen));
        return HttpStatus.OK;
    }

    @Transactional
    public CitizenRegistrationResponse save(String email,
                                            CitizenRegistrationRequest citizenRegistrationRequest,
                                            AddressRegistrationRequest addressRegistrationRequest,
                                            HobbyRegistrationRequest hobbyRegistrationRequest) {

//        Address address = new Address(addressRegistrationRequest);
//        AddressResponse  addressResponse = addressRepository.save(new AddressEntity(address));
//        Hobby hobby = new Hobby(hobbyRegistrationRequest);
//        HobbyResponse hobbyResponse = hobbyRepository.save(hobby);
//        Citizen citizen = new Citizen(citizenRegistrationRequest, addressRegistrationRequest, hobbyRegistrationRequest);
//        citizenRepository.save(new CitizenEntity(citizen));
//        CitizenHobby CitizenHobby = new CitizenHobby(citizen, hobby);
//        citizenHobbyRepository.save(CitizenHobby);

        return null;
    }
}
