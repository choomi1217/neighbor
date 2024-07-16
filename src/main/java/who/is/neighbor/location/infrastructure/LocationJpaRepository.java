package who.is.neighbor.location.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;
import who.is.neighbor.location.infrastructure.entity.CitizenLocationEntity;

import java.util.List;

public interface LocationJpaRepository extends JpaRepository<CitizenLocationEntity, Long> {

    @Query(value = """
            SELECT cl.citizen
                FROM CitizenLocationEntity cl
                WHERE ST_DistanceSphere(
                          ST_MakePoint(cl.longitude, cl.latitude)::geography,
                          ST_MakePoint(:longitude, :latitude)::geography
                      ) <= 1000
            """, nativeQuery = true)
    List<CitizenEntity> findNearByCitizen(@Param(value = "latitude") double latitude, @Param(value = "longitude") double longitude);
}
