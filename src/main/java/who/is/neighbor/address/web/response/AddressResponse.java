package who.is.neighbor.address.web.response;

import who.is.neighbor.address.domain.EupMyeonDong;
import who.is.neighbor.address.domain.SiDo;
import who.is.neighbor.address.domain.SiGunGu;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;

public record AddressResponse(
        SiDo sido,
        SiGunGu sigungu,
        EupMyeonDong eupMyeonDong,
        String detailAddress,
        String addressType,
        String addressVerificationStatus
) {
    public AddressResponse(AddressRegistrationRequest citizenRegistrationRequest) {
        this(
                new SiDo(citizenRegistrationRequest.sido()),
                new SiGunGu(citizenRegistrationRequest.sigungu()),
                new EupMyeonDong(citizenRegistrationRequest.eupMyeonDong()),
                citizenRegistrationRequest.detailAddress(),
                citizenRegistrationRequest.addressType(),
                citizenRegistrationRequest.addressVerificationStatus()
        );
    }
}
