package who.is.neighbor.hobby.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import who.is.neighbor.citizen.infrastructure.entity.CitizenHobbyEntity;
import who.is.neighbor.hobby.domain.Hobby;
import who.is.neighbor.hobby.web.response.HobbyResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class HobbyEntity {
    @Id
    private Long hobbyId;

    private String hobby;

    private LocalDate createdAt;

    @OneToMany(mappedBy = "hobby")
    private List<CitizenHobbyEntity> citizens = new ArrayList<>();

    public HobbyEntity(Hobby hobby) {
        this.hobby = hobby.hobby();
        this.createdAt = LocalDate.now();
    }

    public HobbyEntity() {

    }

    public HobbyEntity(long id, Hobby hobby) {
        this.hobbyId = id;
        this.hobby = hobby.hobby();
        this.createdAt = hobby.createdAt();
    }

    public Hobby toDomain() {
        return new Hobby(hobbyId, hobby, createdAt);
    }
}
