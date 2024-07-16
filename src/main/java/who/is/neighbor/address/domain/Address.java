package who.is.neighbor.address.domain;

import who.is.neighbor.address.application.AddressType;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;

public record Address(long id,
                      String detailAddress,
                      AddressType addressType,
                      Boolean addressVerificationStatus,
                      Sido sidoDomain,
                      Sigungu sigunguDomain,
                      Eupmyeondong eupmyeondongDomain) {

    public static Address from(AddressRegistrationRequest request) {
        return new Address(0,
                request.detailAddress(),
                request.addressType(),
                false,
                null,
                null,
                null
        );
    }
}
