package who.is.neighbor.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.address.infrastructure.entity.SiDoEntity;
import who.is.neighbor.address.infrastructure.entity.SiGunGuEntity;

import java.util.List;

public interface SigunguRepository extends JpaRepository<SiGunGuEntity, Long> {
    List<SiGunGuEntity> findBySiDo(SiDoEntity sido);

    SiGunGuEntity findBySiDoAndSiGunGuName(SiDoEntity sido, String sigunguName);

    SiGunGuEntity findBySiGunGuName(String sigungu);
}
