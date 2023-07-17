package who.is.neighbor.hobby.infrastructure.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
public class HobbyEntity {
    @Id
    private Long id;

}
