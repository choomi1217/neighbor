package who.is.neighbor.address.infrastructure.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.locationtech.jts.geom.Geometry;
import who.is.neighbor.address.domain.SiDo;

@Entity
public class SiDoEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long siDoId;

    private String siDoName;

    private Geometry polygon;

    public SiDo toDomain() {
        return new SiDo(siDoName);
    }
}
