package who.is.neighbor.hobby.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import who.is.neighbor.hobby.domain.Hobby;
import who.is.neighbor.hobby.domain.HobbyRepository;
import who.is.neighbor.hobby.infrastructure.jpa.HobbyEntity;
import who.is.neighbor.hobby.infrastructure.jpa.HobbyJpaRepository;
import who.is.neighbor.hobby.web.response.HobbyResponse;

@Repository
@RequiredArgsConstructor
public class HobbyDao implements HobbyRepository {
    private final HobbyJpaRepository hobbyJpaRepository;
    @Override
    public HobbyResponse save(Hobby hobby) {
        HobbyEntity hobbyEntity = hobbyJpaRepository.save(new HobbyEntity(hobby));
        return hobbyEntity.toDomain();
    }
}
