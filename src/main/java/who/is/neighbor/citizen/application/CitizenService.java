package who.is.neighbor.citizen.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.citizen.domain.CitizenRepository;
import who.is.neighbor.citizen.web.request.CitizenRegistrationRequest;
import who.is.neighbor.citizen.web.response.CitizenResponse;
import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;
import who.is.neighbor.hobby.web.response.HobbyResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CitizenService {
    private final CitizenRepository citizenRepository;
    public Citizen save(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    public List<Citizen> findUserByAccountId(Long citizenId) {
        return citizenRepository.findByCitizenId(citizenId);
    }

    public Citizen delete(Long accountId) {
        return citizenRepository.delete(accountId);
    }

    public CitizenResponse save(String email,
                                CitizenRegistrationRequest citizenRegistrationRequest,
                                AddressRegistrationRequest addressRegistrationRequest,
                                HobbyRegistrationRequest hobbyRegistrationRequest) {
        Citizen citizen = new Citizen(citizenRegistrationRequest, addressRegistrationRequest, hobbyRegistrationRequest);
        Citizen saved = citizenRepository.save(citizen);
        AddressResponse addressResponse = new AddressResponse(saved.addressRegistrationRequest());
        HobbyResponse hobbyResponse = new HobbyResponse(saved.hobbyRegistrationRequest());
        return new CitizenResponse(saved.nickname(),
                saved.phoneNumber(),
                saved.createdAt(),
                addressResponse,
                hobbyResponse);
    }
}
