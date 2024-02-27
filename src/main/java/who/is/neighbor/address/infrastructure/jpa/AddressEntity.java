package who.is.neighbor.address.infrastructure.jpa;

import jakarta.persistence.*;
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


    public AddressResponse toDomain() {
        return null;
    }
}
