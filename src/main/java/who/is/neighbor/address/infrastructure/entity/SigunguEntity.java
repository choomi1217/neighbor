package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.locationtech.jts.geom.Polygon;
import org.postgresql.geometric.PGpolygon;
import who.is.neighbor.address.domain.Sigungu;

import java.util.List;

@Entity
@Getter
public class SigunguEntity {

    @Id
    private String sigunguId;

    private String sigunguName;

    private Polygon polygon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sidoId")
    private SidoEntity sido;

    @OneToMany(mappedBy = "sigungu")
    private List<EupmyeondongEntity> eupmyeondongEntity;

    public SigunguEntity() {

    }

    public SigunguEntity(SidoEntity sidoEntity, Sigungu sigungu) {
        this.sido = sidoEntity;
        this.sigunguName = sigungu.sigunguName();
    }

    public SigunguEntity(Sigungu sigungu) {
        this.sigunguName = sigungu.sigunguName();
    }

    public SigunguEntity(String id, String name, Polygon geometry) {
        this.sigunguId = id;
        this.sigunguName = name;
        this.polygon = geometry;
    }

    public Sigungu toDomain() {
        return new Sigungu(sido.getSidoName(), sigunguName);
    }
}
