package who.is.neighbor.address.infrastructure.jpa;

import jakarta.persistence.*;
import org.postgresql.geometric.PGpolygon;
import who.is.neighbor.address.domain.EupMyeonDong;

@Entity
public class EupMyeonDongEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long eupMyeonDongId;

    private String eupMyeonDongName;

    private PGpolygon polygon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sidoId")
    private SiDoEntity siDo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="siGunGuId")
    private SiGunGuEntity siGunGu;

    public EupMyeonDong toDomain() {
        return new EupMyeonDong(eupMyeonDongName);
    }
}
