package who.is.neighbor.citizen.infrastructure.jpa;

import java.io.Serializable;

public class CitizenHobbyId implements Serializable {
    private Long citizenId;
    private Long hobbyId;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
