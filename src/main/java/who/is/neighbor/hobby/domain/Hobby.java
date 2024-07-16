package who.is.neighbor.hobby.domain;

import java.time.LocalDate;

public record Hobby(long id, String hobby, LocalDate createdAt) {
    public static Hobby from(String hobby) {
        return new Hobby(0, hobby, LocalDate.now());
    }
}
