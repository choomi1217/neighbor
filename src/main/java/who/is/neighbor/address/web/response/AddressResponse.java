package who.is.neighbor.address.web.response;

import who.is.neighbor.address.domain.EupMyeonDong;
import who.is.neighbor.address.domain.Sido;
import who.is.neighbor.address.domain.Sigungu;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;

public record AddressResponse(
        Sido sido,
        Sigungu sigungu,
        EupMyeonDong eupMyeonDong,
        String detailAddress,
        String addressType,
        String addressVerificationStatus
) {
    public AddressResponse(AddressRegistrationRequest citizenRegistrationRequest) {
        this(
                new Sido(citizenRegistrationRequest.sido()),
                new Sigungu(citizenRegistrationRequest.sigungu()),
                new EupMyeonDong(citizenRegistrationRequest.eupMyeonDong()),
                citizenRegistrationRequest.detailAddress(),
                citizenRegistrationRequest.addressType(),
                citizenRegistrationRequest.addressVerificationStatus()
        );
    }
}
