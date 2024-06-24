package who.is.neighbor.account.domain;

import who.is.neighbor.enums.AccountActiveStatus;
import who.is.neighbor.enums.AccountEmailVerificationStatus;

import java.util.UUID;

public record Account(Long accountId,
                      String email,
                      String password,
                      String nickname,
                      String phoneNumber,
                      String emailVerificationToken,
                      AccountEmailVerificationStatus emailVerificationStatus,
                      AccountActiveStatus activeStatus) {

    public Account(String email, String password, String nickname, String phoneNumber) {
        this(null,
                email,
                password,
                nickname,
                phoneNumber,
                generateEmailVerificationToken(),
                AccountEmailVerificationStatus.NON_VERIFIED,
                AccountActiveStatus.ACTIVE
        );
    }

    private static String generateEmailVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public Account verifyEmail() {
        return new Account(
                accountId,
                email,
                password,
                nickname,
                phoneNumber,
                emailVerificationToken,
                AccountEmailVerificationStatus.VERIFIED,
                activeStatus
        );
    }

}
