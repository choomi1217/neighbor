package who.is.neighbor.citizen.infrastructure.jpa;

import java.io.Serializable;

public class CitizenHobbyId implements Serializable {
    private Long citizen;
    private Long hobby;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
