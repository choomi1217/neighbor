package who.is.neighbor.address.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SiDoJpaRepository extends JpaRepository<SiDoEntity, Long> {
    SiDoEntity findBySiDoName(String siDoName);
}
