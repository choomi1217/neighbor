package who.is.neighbor.account.web.response;

import who.is.neighbor.account.application.AccountActiveStatus;
import who.is.neighbor.account.application.AccountEmailVerificationStatus;

public record AccountResponse(String email, AccountEmailVerificationStatus isEmailVerified, AccountActiveStatus isActive){
}
