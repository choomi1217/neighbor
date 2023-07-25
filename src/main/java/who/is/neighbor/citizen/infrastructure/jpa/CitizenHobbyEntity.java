package who.is.neighbor.citizen.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.hobby.domain.Hobby;
import who.is.neighbor.hobby.infrastructure.jpa.HobbyEntity;

@Getter
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

    public CitizenHobbyEntity(Citizen citizen, Hobby hobby) {
        this.citizen = new CitizenEntity(citizen);
        this.hobby = new HobbyEntity(hobby);
    }

    public CitizenHobbyEntity() {

    }
}
