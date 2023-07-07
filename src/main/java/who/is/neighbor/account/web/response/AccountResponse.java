package who.is.neighbor.account.web.response;

import who.is.neighbor.account.domain.AccountActiveStatus;
import who.is.neighbor.account.domain.AccountEmailVerificationStatus;

public record AccountResponse(String email, AccountEmailVerificationStatus isEmailVerified, AccountActiveStatus isActive){
}
