package who.is.neighbor.address.infrastructure.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;

@Entity
public class SiDoEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long siDoId;

    private String siDoName;

    private Geometry polygon;

}
