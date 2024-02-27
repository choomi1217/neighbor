package who.is.neighbor.address.web.request;

public record AddressUpdateRequest(
        String sido,
        String sigungu,
        String eupMyeonDong,
        String detailAddress,
        String addressType,
        String addressVerificationStatus
) {
}
