package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import who.is.neighbor.address.application.AddressType;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.request.AddressUpdateRequest;
import who.is.neighbor.address.web.response.AddressResponse;

@Entity
@Getter
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String detailAddress;

    private AddressType addressType;

    private Boolean addressVerificationStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "si_do_id")
    private SidoEntity sido;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "si_gun_gu_id")
    private SigunguEntity sigungu;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eup_myeon_dong_id")
    private EupmyeondongEntity eupmyeondong;

    public AddressEntity() {

    }

    public AddressEntity(Long id, SidoEntity sido, SigunguEntity sigungu, EupmyeondongEntity eupmyeondong, AddressRegistrationRequest request) {
        this.addressId = id;
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
        this.detailAddress = request.detailAddress();
        this.addressType = request.addressType();
        this.addressVerificationStatus = false;
    }

    public AddressEntity(SidoEntity sido, SigunguEntity sigungu, EupmyeondongEntity eupmyeondong, AddressRegistrationRequest request) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
        this.detailAddress = request.detailAddress();
        this.addressType = request.addressType();
        this.addressVerificationStatus = false;
    }

    public AddressResponse toDomain() {
        return new AddressResponse(
                sido.toDomain(),
                sigungu.toDomain(),
                eupmyeondong.toDomain(),
                detailAddress,
                addressType,
                addressVerificationStatus
        );
    }

    public void update(AddressUpdateRequest request) {
        this.detailAddress = request.detailAddress();
        this.addressType = request.addressType();
        this.addressVerificationStatus = false;
    }

}
