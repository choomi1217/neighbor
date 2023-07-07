package who.is.neighbor.account.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

public record Account (Long accountId, String email, String password, AccountEmailVerificationStatus emailVerificationStatus, AccountActiveStatus activeStatus) {

    public Account(String email, String password) {
        this(null, email, password, AccountEmailVerificationStatus.NON_VERIFIED, AccountActiveStatus.ACTIVE);
    }
}
