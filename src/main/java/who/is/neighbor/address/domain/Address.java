package who.is.neighbor.address.domain;

import who.is.neighbor.address.application.AddressType;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;

public record Address(Sido sido,
                      Sigungu sigungu,
                      EupMyeonDong eupMyeonDong,
                      String detailAddress,
                      AddressType addressType) {
    public Address(AddressRegistrationRequest addressRegistrationRequest) {
        this(new Sido(addressRegistrationRequest.sido()),
             new Sigungu(addressRegistrationRequest.sigungu()),
             new EupMyeonDong(addressRegistrationRequest.eupMyeonDong()),
             addressRegistrationRequest.detailAddress(),
             AddressType.valueOf(addressRegistrationRequest.addressType()));
    }
}
