package who.is.neighbor.citizen.infrastructure.jpa;

import jakarta.persistence.*;
import who.is.neighbor.account.infrastructure.jpa.AccountEntity;
import who.is.neighbor.account.infrastructure.jpa.CitizenActiveStatus;
import who.is.neighbor.address.infrastructure.jpa.AddressEntity;
import who.is.neighbor.citizen.domain.Citizen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CitizenEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long citizenId;
    private String nickname;
    private String phoneNumber;
    private LocalDate createdAt;
    private CitizenActiveStatus activeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    private AccountEntity accountEntity;

    @OneToMany
    @JoinColumn(name = "citizen_address_id")
    private List<AddressEntity> addressEntities = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "citizen_hobby_id")
    private List<CitizenHobbyEntity> hobbyEntities = new ArrayList<>();

    public CitizenEntity(Citizen citizen) {
        this.nickname = citizen.nickname();
        this.createdAt = citizen.createdAt();
        this.phoneNumber = citizen.phoneNumber();
        this.activeStatus = CitizenActiveStatus.ACTIVE;
    }

    public CitizenEntity() {}

    public Citizen toDomain() {

        return null;
    }

    public void delete() {
        this.activeStatus = CitizenActiveStatus.DELETED;
    }
}
