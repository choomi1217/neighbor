package who.is.neighbor.address.domain;

import who.is.neighbor.address.application.AddressType;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;

public record Address(SiDo sido,
                      SiGunGu sigungu,
                      EupMyeonDong eupMyeonDong,
                      String detailAddress,
                      AddressType addressType) {
    public Address(AddressRegistrationRequest addressRegistrationRequest) {
        this(new SiDo(addressRegistrationRequest.sido()),
             new SiGunGu(addressRegistrationRequest.sigungu()),
             new EupMyeonDong(addressRegistrationRequest.eupMyeonDong()),
             addressRegistrationRequest.detailAddress(),
             AddressType.valueOf(addressRegistrationRequest.addressType()));
    }

}
