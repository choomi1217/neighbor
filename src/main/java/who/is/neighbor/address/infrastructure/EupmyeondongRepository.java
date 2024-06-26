package who.is.neighbor.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import who.is.neighbor.address.infrastructure.entity.EupmyeondongEntity;
import who.is.neighbor.address.infrastructure.entity.SidoEntity;
import who.is.neighbor.address.infrastructure.entity.SigunguEntity;

import java.util.List;

public interface EupmyeondongRepository extends JpaRepository<EupmyeondongEntity, Long> {

    @Query("""
            select e from EupmyeondongEntity e where e.sido = :sido and e.sigungu = :sigungu
            """)
    List<EupmyeondongEntity> findBySiDoAndSiGunGu(@Param("sido") SidoEntity sido, @Param("sigungu") SigunguEntity sigungu);

    @Query("""
            select e \
            from EupmyeondongEntity e \
            where e.sido = :sido \
            and e.sigungu = :sigungu \
            and e.eupmyeondongName = :eupmyeondongName
            """)
    EupmyeondongEntity findBySiDoAndSiGunGuAndEupMyeonDongName(@Param("sido") SidoEntity siDo, @Param("sigungu") SigunguEntity sigungu, @Param("eupmyeondongName") String eupmyeondongName);
}
