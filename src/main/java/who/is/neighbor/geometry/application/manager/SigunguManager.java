package who.is.neighbor.geometry.application.manager;

import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.postgresql.geometric.PGpolygon;
import org.springframework.stereotype.Component;
import who.is.neighbor.address.infrastructure.SigunguRepository;
import who.is.neighbor.address.infrastructure.entity.SigunguEntity;

import java.sql.SQLException;

@Component
public class SigunguManager implements GeometryEntityManager {

    private final SigunguRepository repository;

    public SigunguManager(SigunguRepository repository) {
        this.repository = repository;
    }

    @Override
    public void insertTable(FeatureIterator<SimpleFeature> iterator) throws SQLException {
        GeometryEntityManager.super.insertTable(iterator, repository);
    }

    @Override
    public SigunguEntity createEntity(Object id, Object name, PGpolygon geometry) {
        return new SigunguEntity((String) id, (String) name, geometry);
    }
}

