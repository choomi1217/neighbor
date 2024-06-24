package who.is.neighbor.account.web.response;

import who.is.neighbor.enums.AccountActiveStatus;
import who.is.neighbor.enums.AccountEmailVerificationStatus;

public record AccountResponse(String email, String nickname, AccountEmailVerificationStatus isEmailVerified, AccountActiveStatus isActive){
}
