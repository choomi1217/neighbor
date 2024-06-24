package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.*;
import who.is.neighbor.address.domain.Address;
import who.is.neighbor.address.web.request.AddressUpdateRequest;
import who.is.neighbor.address.web.response.AddressResponse;

@Entity
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "si_do_id")
    private SiDoEntity siDo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "si_gun_gu_id")
    private SiGunGuEntity siGunGu;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eup_myeon_dong_id")
    private EupMyeonDongEntity eupMyeonDong;

    public AddressEntity(Address address) {
//        this.siDo = new SiDoEntity(address.sido());
//        this.siGunGu = new SiGunGuEntity(address.sigungu());
//        this.eupMyeonDong = new EupMyeonDongEntity(address.eupMyeonDong());
    }


    public AddressResponse toDomain() {
        return null;
    }

    public void update(AddressUpdateRequest request) {

    }
}
