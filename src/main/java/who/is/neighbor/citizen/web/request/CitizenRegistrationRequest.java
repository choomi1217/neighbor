package who.is.neighbor.citizen.web.request;

import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;

public record CitizenRegistrationRequest(
        String nickname,
        String phoneNumber,
        AddressRegistrationRequest addressRegistrationRequest,
        HobbyRegistrationRequest hobbyRegistrationRequest) {
}
