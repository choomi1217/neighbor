package who.is.neighbor.citizen.domain;

import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.citizen.web.request.CitizenRegistrationRequest;
import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;

import java.time.LocalDate;

public record Citizen(
        String nickname,
        LocalDate createdAt,
        String phoneNumber,
        AddressRegistrationRequest addressRegistrationRequest,
        HobbyRegistrationRequest hobbyRegistrationRequest
){
    public Citizen(CitizenRegistrationRequest citizenRegistrationRequest,
                   AddressRegistrationRequest addressRegistrationRequest,
                   HobbyRegistrationRequest hobbyRegistrationRequest) {
        this(
                citizenRegistrationRequest.nickname(),
                LocalDate.now(),
                citizenRegistrationRequest.phoneNumber(),
                addressRegistrationRequest,
                hobbyRegistrationRequest
        );
    }
}
