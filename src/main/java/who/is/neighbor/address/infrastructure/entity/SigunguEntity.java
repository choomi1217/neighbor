package who.is.neighbor.address.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.postgresql.geometric.PGpolygon;
import who.is.neighbor.address.domain.Sigungu;

import java.util.List;

@Entity
@Getter
public class SigunguEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long sigunguId;

    private String sigunguName;

    private PGpolygon polygon;

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

    public Sigungu toDomain() {
        return new Sigungu(sido.getSidoName(), sigunguName);
    }
}
