package who.is.neighbor.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.address.infrastructure.entity.EupMyeonDongEntity;
import who.is.neighbor.address.infrastructure.entity.SiDoEntity;
import who.is.neighbor.address.infrastructure.entity.SiGunGuEntity;

import java.util.List;

public interface EupMyeonDongRepository extends JpaRepository<EupMyeonDongEntity, Long> {
    List<EupMyeonDongEntity> findBySiDoAndSiGunGu(SiDoEntity sido, SiGunGuEntity sigungu);
}
