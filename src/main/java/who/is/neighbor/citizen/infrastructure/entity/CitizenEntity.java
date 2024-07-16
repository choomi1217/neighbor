package who.is.neighbor.citizen.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import who.is.neighbor.account.infrastructure.entity.AccountEntity;
import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.enums.CitizenActiveStatus;
import who.is.neighbor.address.infrastructure.entity.AddressEntity;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.hobby.infrastructure.entity.HobbyEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
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
        this.addressEntities = new ArrayList<>();
        this.hobbyEntities = new ArrayList<>();
    }

    public CitizenEntity() {}

    public CitizenEntity(long id, String nickname, LocalDate createdAt, String phoneNumber, AddressEntity addressEntity, HobbyEntity hobbyEntity) {
        this.citizenId = id;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.phoneNumber = phoneNumber;
        if (addressEntity != null) {
            this.addressEntities = List.of(addressEntity);
        }
        if (hobbyEntity != null) {
            this.hobbyEntities = List.of(new CitizenHobbyEntity(this, hobbyEntity));
        }
    }

    public Citizen toDomain() {

        return new Citizen(
                this.nickname,
                this.createdAt,
                this.phoneNumber,
                this.addressEntities.stream().map(AddressEntity::toDomain).toList(),
                this.hobbyEntities.stream().map(citizenHobbyEntity -> citizenHobbyEntity.getHobby().toDomain()).toList()
        );
    }

    public void delete() {
        this.activeStatus = CitizenActiveStatus.DELETED;
    }

    public void addHobby(HobbyEntity hobbyEntity) {
        this.hobbyEntities.add(new CitizenHobbyEntity(this, hobbyEntity));
    }

    public void addAddress(AddressEntity addressEntity) {
        this.addressEntities.add(addressEntity);
    }
}
