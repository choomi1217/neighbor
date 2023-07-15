package who.is.neighbor.account.domain;

import who.is.neighbor.account.application.AccountActiveStatus;
import who.is.neighbor.account.application.AccountEmailVerificationStatus;

import java.util.UUID;

public record Account (Long accountId, String email, String password, String emailVerificationToken , AccountEmailVerificationStatus emailVerificationStatus, AccountActiveStatus activeStatus) {

    public Account(String email, String password) {
        this(null,
                email,
                password,
                generateEmailVerificationToken(),
                AccountEmailVerificationStatus.NON_VERIFIED, AccountActiveStatus.ACTIVE);
    }

    private static String generateEmailVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public Account verifyEmail() {
        return new Account(
                accountId,
                email,
                password,
                emailVerificationToken,
                AccountEmailVerificationStatus.VERIFIED,
                activeStatus
        );
    }

}
