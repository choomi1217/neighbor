package who.is.neighbor.citizen.infrastructure.jpa;

import jakarta.persistence.*;
import who.is.neighbor.account.infrastructure.jpa.AccountEntity;
import who.is.neighbor.citizen.domain.Citizen;

import java.time.LocalDate;

@Entity
public class CitizenEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long citizenId;

    private String nickname;

    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private AccountEntity accountEntity;

    public CitizenEntity(Citizen user) {
        this.nickname = user.nickname();
        this.createdAt = user.createdAt();
    }

    public CitizenEntity() {}

    public Citizen toDomain() {
        return new Citizen( this.nickname, this.createdAt);
    }
}
