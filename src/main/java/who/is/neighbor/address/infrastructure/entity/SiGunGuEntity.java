package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.*;
import org.postgresql.geometric.PGpolygon;
import who.is.neighbor.address.domain.SiGunGu;

import java.util.List;

@Entity
public class SiGunGuEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long siGunGuId;

    private String siGunGuName;

    private PGpolygon polygon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="siDoId")
    private SiDoEntity siDo;

    @OneToMany(mappedBy = "siGunGu")
    private List<EupMyeonDongEntity> eupMyeonDongEntity;

    public SiGunGu toDomain() {
        return new SiGunGu(siGunGuName);
    }
}
