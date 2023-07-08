package who.is.neighbor.address.infrastructure.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.locationtech.jts.geom.Geometry;

@Entity
public class SiGunGuEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long siGunGuId;

    private String siGunGuName;

    private Geometry polygon;

}
