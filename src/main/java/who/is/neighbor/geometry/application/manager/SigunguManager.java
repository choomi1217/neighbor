package who.is.neighbor.geometry.application.manager;

import org.geotools.feature.FeatureIterator;
import org.locationtech.jts.geom.Polygon;
import org.opengis.feature.simple.SimpleFeature;
import org.postgresql.geometric.PGpolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import who.is.neighbor.address.infrastructure.SigunguRepository;
import who.is.neighbor.address.infrastructure.entity.SigunguEntity;

import java.sql.SQLException;

@Component
public class SigunguManager implements GeometryEntityManager<SigunguEntity> {

    private final SigunguRepository repository;

    @Autowired
    public SigunguManager(SigunguRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void insertTable(FeatureIterator<SimpleFeature> iterator) throws SQLException {
        GeometryEntityManager.super.insertTable(iterator, repository);
    }

    @Override
    public SigunguEntity createEntity(Object id, Object name, Polygon geometry) {
        return new SigunguEntity((String) id, (String) name, geometry);
    }
}

