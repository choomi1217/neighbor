package who.is.neighbor.citizen.infrastructure.jpa;

import jakarta.persistence.*;
import who.is.neighbor.account.infrastructure.jpa.AccountEntity;
import who.is.neighbor.address.infrastructure.jpa.AddressEntity;
import who.is.neighbor.citizen.domain.Citizen;

import java.time.LocalDate;

@Entity
public class CitizenEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long citizenId;

    private String nickname;

    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    private AccountEntity accountEntity;

    @OneToOne(mappedBy = "citizenEntity", cascade = CascadeType.ALL)
    private AddressEntity addressEntity;

    public CitizenEntity(Citizen user) {
        this.nickname = user.nickname();
        this.createdAt = user.createdAt();
    }

    public CitizenEntity() {}

    public Citizen toDomain() {
        return new Citizen( this.nickname, this.createdAt);
    }
}
