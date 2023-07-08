package who.is.neighbor.address.infrastructure.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.locationtech.jts.geom.Geometry;

@Entity
public class EupMyeonDongEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long eupMyeonDongId;

    private String eupMyeonDongName;

    private Geometry polygon;
}
