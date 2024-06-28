package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.locationtech.jts.geom.Polygon;
import org.postgresql.geometric.PGpolygon;
import who.is.neighbor.address.domain.Eupmyeondong;

@Getter
@Entity
public class EupmyeondongEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long eupmyeondongId;

    private String eupmyeondongName;

    @Column(columnDefinition = "geometry(POLYGON, 4326)")
    private Polygon polygon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sidoId")
    private SidoEntity sido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sigunguId")
    private SigunguEntity sigungu;

    public EupmyeondongEntity() {

    }

    public EupmyeondongEntity(SidoEntity sidoEntity, SigunguEntity sigunguEntity, Eupmyeondong eupmyeondong) {
        this.sido = sidoEntity;
        this.sigungu = sigunguEntity;
        this.eupmyeondongName = eupmyeondong.eupmyeondongName();
    }

    public EupmyeondongEntity(Eupmyeondong eupmyeondong) {
        this.eupmyeondongName = eupmyeondong.eupmyeondongName();
    }

    public Eupmyeondong toDomain() {
        return new Eupmyeondong(sido.getSidoName(), sigungu.getSigunguName(), eupmyeondongName);
    }
}
