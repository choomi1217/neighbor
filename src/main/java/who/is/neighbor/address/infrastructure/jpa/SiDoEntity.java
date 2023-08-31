package who.is.neighbor.address.infrastructure.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.postgresql.geometric.PGpolygon;
import who.is.neighbor.address.domain.SiDo;

@Entity
public class SiDoEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long siDoId;

    private String siDoName;

    private PGpolygon polygon;

    public SiDo toDomain() {
        return new SiDo(siDoName);
    }
}
