package who.is.neighbor.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.address.infrastructure.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
