package who.is.neighbor.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import who.is.neighbor.address.infrastructure.entity.SidoEntity;
import who.is.neighbor.address.infrastructure.entity.SigunguEntity;

import java.util.List;

public interface SigunguRepository extends JpaRepository<SigunguEntity, Long> {

    @Query("select s from SigunguEntity s where s.sido = :sido")
    List<SigunguEntity> findBySido(@Param("sido") SidoEntity sido);

    @Query("select s from SigunguEntity s where s.sido = :sido and s.sigunguName = :sigunguName")
    SigunguEntity findBySidoAndSigunguName(@Param("sido") SidoEntity sido, @Param("sigunguName") String sigunguName);

}
