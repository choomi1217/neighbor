package who.is.neighbor.geometry.application.manager;

import org.geotools.feature.FeatureIterator;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.geom.Polygon;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.transaction.annotation.Transactional;
import who.is.neighbor.address.infrastructure.GeometryRepository;

import java.sql.SQLException;

public interface GeometryEntityManager<T> {

    @Transactional
    default void insertTable(FeatureIterator<SimpleFeature> iterator, GeometryRepository<T> repository) throws SQLException {
        WKTReader reader = new WKTReader();
        while (iterator.hasNext()) {
            SimpleFeature feature = iterator.next();

            Object id = feature.getAttribute("ctprvn_cd");
            Object name = feature.getAttribute("ctp_kor_nm");

            Geometry geometry;
            try {
                geometry = reader.read(feature.getDefaultGeometry().toString());
            } catch (Exception e) {
                throw new SQLException("Error reading geometry", e);
            }


            T entity = createEntity(id, name, (Polygon) geometry);
            repository.save(entity);
        }
    }

    void insertTable(FeatureIterator<SimpleFeature> iterator) throws SQLException;

    T createEntity(Object id, Object name, Polygon geometry);
}
