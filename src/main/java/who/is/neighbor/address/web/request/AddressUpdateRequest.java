package who.is.neighbor.address.web.request;

import who.is.neighbor.address.application.AddressType;

public record AddressUpdateRequest(
        String sido,
        String sigungu,
        String eupMyeonDong,
        String detailAddress,
        AddressType addressType
) {
}
