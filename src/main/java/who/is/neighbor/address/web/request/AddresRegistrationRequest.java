package who.is.neighbor.address.web.request;

import who.is.neighbor.address.application.AddressType;

public record AddresRegistrationRequest(
        int sidoId,
        int sigunguId,
        int eupmyeondongId,
        String detailAddress,
        AddressType addressType
) {
}
