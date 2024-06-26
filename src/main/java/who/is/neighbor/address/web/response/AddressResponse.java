package who.is.neighbor.address.web.response;

import who.is.neighbor.address.application.AddressType;
import who.is.neighbor.address.domain.Eupmyeondong;
import who.is.neighbor.address.domain.Sido;
import who.is.neighbor.address.domain.Sigungu;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;

public record AddressResponse(
        Sido sido,
        Sigungu sigungu,
        Eupmyeondong eupMyeonDong,
        String detailAddress,
        AddressType addressType,
        Boolean addressVerificationStatus
) {
    public AddressResponse(AddressRegistrationRequest citizenRegistrationRequest, Boolean addressVerificationStatus) {
        this(
                new Sido(citizenRegistrationRequest.sidoName()),
                new Sigungu(citizenRegistrationRequest.sidoName(), citizenRegistrationRequest.sigunguName()),
                new Eupmyeondong(citizenRegistrationRequest.sidoName(), citizenRegistrationRequest.sigunguName(), citizenRegistrationRequest.eupmyeondongName()),
                citizenRegistrationRequest.detailAddress(),
                citizenRegistrationRequest.addressType(),
                addressVerificationStatus
        );
    }
}
