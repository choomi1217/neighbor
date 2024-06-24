package who.is.neighbor.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.address.infrastructure.entity.SiDoEntity;

public interface SiDoRepository extends JpaRepository<SiDoEntity, Long> {
    SiDoEntity findBySiDoName(String siDoName);
}
