package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import org.locationtech.jts.geom.Polygon;
import who.is.neighbor.address.domain.Sido;

import java.util.List;

@Entity
@Getter
public class SidoEntity {
    @Id
    private String sidoId;

    private String sidoName;

    private Polygon polygon;

    @OneToMany(mappedBy = "sido")
    private List<SigunguEntity> siGunGuEntity;

    @OneToMany(mappedBy = "sido")
    private List<EupmyeondongEntity> eupMyeonDongEntities;

    public SidoEntity() {

    }

    public SidoEntity(Sido sido) {
        this.sidoName = sido.sidoName();
    }

    public SidoEntity(Object sidoId, Object sidoName, Polygon geometry) {
        this.sidoId = (String) sidoId;
        this.sidoName = (String) sidoName;
        this.polygon = geometry;
    }

    public Sido toDomain() {
        return new Sido(sidoName);
    }
}
