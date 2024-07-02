package who.is.neighbor.geometry.application.manager;

import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.postgresql.geometric.PGpolygon;
import org.springframework.stereotype.Component;
import who.is.neighbor.address.infrastructure.SidoRepository;
import who.is.neighbor.address.infrastructure.entity.SidoEntity;

import java.sql.SQLException;

@Component
public class SidoManager implements GeometryEntityManager {

    private final SidoRepository repository;

    public SidoManager(SidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void insertTable(FeatureIterator<SimpleFeature> iterator) throws SQLException {
        GeometryEntityManager.super.insertTable(iterator, repository);
    }

    @Override
    public SidoEntity createEntity(Object id, Object name, PGpolygon geometry) {
        return new SidoEntity(id, name, geometry);
    }
}

