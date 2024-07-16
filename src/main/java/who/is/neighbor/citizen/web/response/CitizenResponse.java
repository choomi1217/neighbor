package who.is.neighbor.citizen.web.response;

import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.hobby.web.response.HobbyResponse;

import java.util.List;

public record CitizenResponse(
        long id,
        String nickname,
        String phoneNumber,
        List<HobbyResponse> hobbyResponses,
        List<AddressResponse> addressResponse
) {
}
