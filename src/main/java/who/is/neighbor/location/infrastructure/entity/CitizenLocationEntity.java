package who.is.neighbor.location.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;

@Getter
@NoArgsConstructor
@Entity
public class CitizenLocationEntity {

    @Id
    private Long citizenId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "citizen_id")
    private CitizenEntity citizen;

    private double latitude;

    private double longitude;

    public CitizenLocationEntity(long citizenId, double latitude, double longitude) {
        this.citizenId = citizenId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void updateLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
