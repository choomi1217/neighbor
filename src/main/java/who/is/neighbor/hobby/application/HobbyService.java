package who.is.neighbor.hobby.application;

import org.springframework.stereotype.Service;
import who.is.neighbor.hobby.domain.Hobby;
import who.is.neighbor.hobby.infrastructure.HobbyJpaRepository;
import who.is.neighbor.hobby.infrastructure.entity.HobbyEntity;
import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;
import who.is.neighbor.hobby.web.response.HobbyResponse;

import java.util.List;

@Service
public class HobbyService {

    private final HobbyJpaRepository hobbyRepository;

    public HobbyService(HobbyJpaRepository hobbyRepository) {
        this.hobbyRepository = hobbyRepository;
    }

    public List<HobbyResponse> findHobbies() {
        List<HobbyEntity> hobbyEntities = hobbyRepository.findAll();
        return hobbyEntities.stream()
                .map(HobbyEntity::toDomain)
                .map(hobby -> new HobbyResponse(hobby.id(), hobby.hobby()))
                .toList();
    }

    public HobbyResponse save(HobbyRegistrationRequest request) {
        Hobby hobby = Hobby.from(request.hobby());
        HobbyEntity hobbyEntity = hobbyRepository.save(new HobbyEntity(hobby));
        return new HobbyResponse(hobbyEntity.getHobbyId(), hobbyEntity.getHobby());
    }

    public void delete(long id) {

    }
}
