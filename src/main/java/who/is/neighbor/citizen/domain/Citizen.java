package who.is.neighbor.citizen.domain;

import java.time.LocalDate;

public record Citizen(String nickname, LocalDate createdAt){
    public Citizen(String nickname) {
        this(nickname, LocalDate.now());
    }
}
