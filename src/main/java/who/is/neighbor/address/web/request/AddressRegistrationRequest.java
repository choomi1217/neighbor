package who.is.neighbor.address.web.request;

import who.is.neighbor.address.application.AddressType;

public record AddressRegistrationRequest(
        String sidoName,
        String sigunguName,
        String eupmyeondongName,
        String detailAddress,
        AddressType addressType
){
}
