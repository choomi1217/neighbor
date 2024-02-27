package who.is.neighbor.address.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SigunguJpaRepository extends JpaRepository<SiGunGuEntity, Long> {
    List<SiGunGuEntity> findBySiDo(SiDoEntity sido);

    SiGunGuEntity findBySiDoAndSiGunGuName(SiDoEntity sido, String sigunguName);

    SiGunGuEntity findBySiGunGuName(String sigungu);
}
