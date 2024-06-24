package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.postgresql.geometric.PGpolygon;
import who.is.neighbor.address.domain.SiDo;

import java.util.List;

@Entity
public class SiDoEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long siDoId;

    private String siDoName;

    private PGpolygon polygon;

    @OneToMany(mappedBy = "siDo")
    private List<SiGunGuEntity> siGunGuEntity;

    @OneToMany(mappedBy = "siDo")
    private List<EupMyeonDongEntity> eupMyeonDongEntities;

    public SiDo toDomain() {
        return new SiDo(siDoName);
    }
}
