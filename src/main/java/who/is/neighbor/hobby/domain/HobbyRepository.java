package who.is.neighbor.hobby.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.hobby.infrastructure.entity.HobbyEntity;

public interface HobbyRepository extends JpaRepository<HobbyEntity, Long>{
}
