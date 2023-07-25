package who.is.neighbor.hobby.domain;

import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;

import java.time.LocalDate;

public record Hobby(String hobby, LocalDate createdAt) {
    public Hobby(HobbyRegistrationRequest hobbyRegistrationRequest) {
        this(hobbyRegistrationRequest.hobby(), LocalDate.now());
    }
}
