package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.*;
import who.is.neighbor.address.application.AddressType;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.request.AddressUpdateRequest;
import who.is.neighbor.address.web.response.AddressResponse;

@Entity
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String detailAddress;

    private AddressType addressType;

    private Boolean addressVerificationStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "si_do_id")
    private SidoEntity siDo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "si_gun_gu_id")
    private SigunguEntity siGunGu;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eup_myeon_dong_id")
    private EupmyeondongEntity eupMyeonDong;

    public AddressEntity() {

    }

    public AddressEntity(SidoEntity siDo, SigunguEntity sigungu, EupmyeondongEntity eupMyeonDong, AddressRegistrationRequest request) {
        this.siDo = siDo;
        this.siGunGu = sigungu;
        this.eupMyeonDong = eupMyeonDong;
        this.detailAddress = request.detailAddress();
        this.addressType = request.addressType();
        this.addressVerificationStatus = false;
    }

    public AddressResponse toDomain() {
        return null;
    }

    public void update(AddressUpdateRequest request) {
        this.detailAddress = request.detailAddress();
        this.addressType = request.addressType();
        this.addressVerificationStatus = request.addressVerificationStatus();
    }
}
