package who.is.neighbor.address.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EupMyeonDongJpaRepository extends JpaRepository<EupMyeonDongEntity, Long> {
    List<EupMyeonDongEntity> findBySiDoAndSiGunGu(SiDoEntity sido, SiGunGuEntity sigungu);
}
