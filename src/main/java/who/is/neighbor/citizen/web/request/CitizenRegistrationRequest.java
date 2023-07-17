package who.is.neighbor.citizen.web.request;

import who.is.neighbor.address.web.request.AddresRegistrationRequest;
import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;

public record CitizenRegistrationRequest(
        String nickname,
        String phoneNumber,
        AddresRegistrationRequest addressRegistrationRequest,
        HobbyRegistrationRequest hobbyRegistrationRequest) {
}
