package who.is.neighbor.geometry.application.manager;

import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.postgresql.geometric.PGpolygon;
import org.springframework.stereotype.Component;
import who.is.neighbor.address.infrastructure.EupmyeondongRepository;
import who.is.neighbor.address.infrastructure.entity.EupmyeondongEntity;

import java.sql.SQLException;

@Component
public class EupmyeondongManager implements GeometryEntityManager<EupmyeondongEntity> {

    private final EupmyeondongRepository repository;

    public EupmyeondongManager(EupmyeondongRepository repository) {
        this.repository = repository;
    }

    @Override
    public void insertTable(FeatureIterator<SimpleFeature> iterator) throws SQLException {
        GeometryEntityManager.super.insertTable(iterator, repository);
    }

    @Override
    public EupmyeondongEntity createEntity(Object id, Object name, PGpolygon geometry) {
        return new EupmyeondongEntity((String) id, String.valueOf(name), geometry);
    }
}
