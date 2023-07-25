package who.is.neighbor.hobby.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyJpaRepository extends JpaRepository<HobbyEntity,Long> {
}
