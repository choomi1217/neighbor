package who.is.neighbor.citizen.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import who.is.neighbor.address.domain.Address;
import who.is.neighbor.address.domain.AddressRepository;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.citizen.domain.CitizenHobby;
import who.is.neighbor.citizen.domain.CitizenHobbyRepository;
import who.is.neighbor.citizen.domain.CitizenRepository;
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
    private final CitizenRepository citizenRepository;
    private final AddressRepository addressRepository;
    private final HobbyRepository hobbyRepository;
    private final CitizenHobbyRepository citizenHobbyRepository;

    public List<Citizen> findUserByAccountId(Long citizenId) {
        return citizenRepository.findByCitizenId(citizenId);
    }

    public Citizen delete(Long accountId) {
        return citizenRepository.delete(accountId);
    }

    @Transactional
    public CitizenRegistrationResponse save(String email,
                                            CitizenRegistrationRequest citizenRegistrationRequest,
                                            AddressRegistrationRequest addressRegistrationRequest,
                                            HobbyRegistrationRequest hobbyRegistrationRequest) {

        Address address = new Address(addressRegistrationRequest);
        AddressResponse  addressResponse = addressRepository.save(address);
        Hobby hobby = new Hobby(hobbyRegistrationRequest);
        HobbyResponse hobbyResponse = hobbyRepository.save(hobby);
        Citizen citizen = new Citizen(citizenRegistrationRequest, addressRegistrationRequest, hobbyRegistrationRequest);
        CitizenResponse citizenResponse = citizenRepository.save(citizen);
        CitizenHobby CitizenHobby = new CitizenHobby(citizen, hobby);
        citizenHobbyRepository.save(CitizenHobby);

        return new CitizenRegistrationResponse(citizenResponse, addressResponse, hobbyResponse);
    }
}
