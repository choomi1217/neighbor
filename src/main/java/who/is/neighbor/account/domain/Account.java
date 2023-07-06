package who.is.neighbor.account.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

public record Account (Long accountId, String email, String password, String phoneNumber, boolean isEmailVerified, boolean isActive) {

    public Account(String email, String password, String phoneNumber) {
        this(null, email, password, phoneNumber, false,  true);
    }
}
