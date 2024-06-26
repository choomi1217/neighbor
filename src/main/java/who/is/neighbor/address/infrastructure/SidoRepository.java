package who.is.neighbor.address.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import who.is.neighbor.address.infrastructure.entity.EupmyeondongEntity;
import who.is.neighbor.address.infrastructure.entity.SidoEntity;

public interface SidoRepository extends GeometryRepository<SidoEntity> {
    SidoEntity findBySidoName(String siDoName);
}
