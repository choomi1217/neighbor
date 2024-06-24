package who.is.neighbor.hobby.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.hobby.infrastructure.entity.HobbyEntity;

public interface HobbyJpaRepository extends JpaRepository<HobbyEntity,Long> {
}
