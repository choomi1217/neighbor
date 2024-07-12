package who.is.neighbor.geometry.application.manager;

import org.springframework.stereotype.Component;
import who.is.neighbor.address.infrastructure.EupmyeondongRepository;
import who.is.neighbor.address.infrastructure.SidoRepository;
import who.is.neighbor.address.infrastructure.SigunguRepository;
import who.is.neighbor.enums.GeometryTableFactory;

@Component
public class ManagerFactory {
    private final SidoRepository sidoRepository;
    private final SigunguRepository sigunguRepository;
    private final EupmyeondongRepository eupmyeondongRepository;

    public ManagerFactory(SidoRepository sidoRepository, SigunguRepository sigunguRepository, EupmyeondongRepository eupmyeondongRepository) {
        this.sidoRepository = sidoRepository;
        this.sigunguRepository = sigunguRepository;
        this.eupmyeondongRepository = eupmyeondongRepository;
    }

    public GeometryEntityManager<?> getManager(String tableName) {
        switch (GeometryTableFactory.valueOf(tableName)) {
            case SIDO -> {
                return new SidoManager(sidoRepository);
            }
            case SIGUNGU -> {
                return new SigunguManager(sigunguRepository);
            }
            case EUPMYEONDONG -> {
                return new EupmyeondongManager(eupmyeondongRepository);
            }
            default -> throw new IllegalArgumentException("Invalid Table Name");
        }
    }
}
