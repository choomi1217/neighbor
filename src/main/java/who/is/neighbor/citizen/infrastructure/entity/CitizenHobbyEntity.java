package who.is.neighbor.citizen.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import who.is.neighbor.hobby.infrastructure.entity.HobbyEntity;

@Getter
@RequiredArgsConstructor
@Entity
@IdClass(CitizenHobbyId.class)
public class CitizenHobbyEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizenId")
    private CitizenEntity citizen;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobbyId")
    private HobbyEntity hobby;

    public CitizenHobbyEntity(CitizenEntity citizenEntity, HobbyEntity hobbyEntity) {
        this.citizen = citizenEntity;
        this.hobby = hobbyEntity;
    }
}

