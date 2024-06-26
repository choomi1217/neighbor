package who.is.neighbor.address.domain;

import who.is.neighbor.address.application.AddressType;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;

public record Address(Sido sido,
                      Sigungu sigungu,
                      Eupmyeondong eupMyeonDong,
                      String detailAddress,
                      AddressType addressType) {
    public Address(AddressRegistrationRequest addressRegistrationRequest) {
        this(new Sido(addressRegistrationRequest.sidoName()),
             new Sigungu(addressRegistrationRequest.sidoName(), addressRegistrationRequest.sigunguName()),
             new Eupmyeondong(addressRegistrationRequest.sidoName(), addressRegistrationRequest.sigunguName(), addressRegistrationRequest.eupmyeondongName()),
             addressRegistrationRequest.detailAddress(),
             addressRegistrationRequest.addressType());
    }

}
