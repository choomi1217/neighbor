package who.is.neighbor.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GeometryRepository<T> extends JpaRepository<T, Long> {
}
