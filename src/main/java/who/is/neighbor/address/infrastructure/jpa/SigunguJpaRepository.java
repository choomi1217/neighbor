package who.is.neighbor.address.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SigunguJpaRepository extends JpaRepository<SiGunGuEntity, Long> {
    List<SiGunGuEntity> findBySido(String sido);

    SiGunGuEntity findBySidoAndSigungu(String sidoName, String sigunguName);
}
