package who.is.neighbor.geometry.application.manager;

import org.geotools.feature.FeatureIterator;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeature;
import org.postgresql.geometric.PGpolygon;
import org.springframework.transaction.annotation.Transactional;
import who.is.neighbor.address.infrastructure.GeometryRepository;

import java.sql.SQLException;

public interface GeometryEntityManager<T> {

    @Transactional
    default void insertTable(FeatureIterator<SimpleFeature> iterator, GeometryRepository<T> repository) throws SQLException {
        while (iterator.hasNext()) {
            SimpleFeature feature = iterator.next();

            Object id = feature.getAttribute("ctprvn_cd");
            Object name = feature.getAttribute("ctp_kor_nm");

            Geometry geometry = (Geometry) feature.getDefaultGeometry();
            String wkt = geometry.toText();
            PGpolygon pgGeometry = new PGpolygon(wkt);
            T entity = createEntity(id, name, pgGeometry);
            repository.save(entity);
        }
    }

    void insertTable(FeatureIterator<SimpleFeature> iterator) throws SQLException;

    T createEntity(Object id, Object name, PGpolygon geometry);
}
