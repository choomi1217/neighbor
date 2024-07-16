package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import who.is.neighbor.address.application.AddressType;
import who.is.neighbor.address.domain.Address;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.request.AddressUpdateRequest;

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

    public AddressEntity(Address address) {
        this.addressId = address.id();
        this.detailAddress = address.detailAddress();
        this.addressType = address.addressType();
        this.addressVerificationStatus = address.addressVerificationStatus();
        this.sido = new SidoEntity(address.sidoDomain());
        this.sigungu = new SigunguEntity(address.sigunguDomain());
        this.eupmyeondong = new EupmyeondongEntity(address.eupmyeondongDomain());
    }

    public Address toDomain() {
        return new Address(
                this.addressId,
                this.detailAddress,
                this.addressType,
                this.addressVerificationStatus,
                this.sido.toDomain(),
                this.sigungu.toDomain(),
                this.eupmyeondong.toDomain()
        );
    }

    public void update(AddressUpdateRequest request) {
        this.detailAddress = request.detailAddress();
        this.addressType = request.addressType();
        this.addressVerificationStatus = false;
    }

}
