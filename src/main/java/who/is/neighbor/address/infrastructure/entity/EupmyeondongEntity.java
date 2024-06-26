package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.*;
import org.postgresql.geometric.PGpolygon;
import who.is.neighbor.address.domain.Eupmyeondong;

@Entity
public class EupmyeondongEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long eupmyeondongId;

    private String eupmyeondongName;

    private PGpolygon polygon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sidoId")
    private SidoEntity sido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sigunguId")
    private SigunguEntity sigungu;

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
