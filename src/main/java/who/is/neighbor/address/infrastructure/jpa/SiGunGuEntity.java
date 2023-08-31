package who.is.neighbor.address.infrastructure.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.postgresql.geometric.PGpolygon;
import who.is.neighbor.address.domain.SiGunGu;

@Entity
public class SiGunGuEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long siGunGuId;

    private String siGunGuName;

    private PGpolygon polygon;

    public SiGunGu toDomain() {
        return new SiGunGu(siGunGuName);
    }
}
