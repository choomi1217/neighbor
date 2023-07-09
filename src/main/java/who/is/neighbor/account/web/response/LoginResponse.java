package who.is.neighbor.account.web.response;

import who.is.neighbor.account.domain.AccountEmailVerificationStatus;
import who.is.neighbor.citizen.domain.Citizen;

import java.util.List;

public record LoginResponse(String email, List<Citizen> userList, AccountEmailVerificationStatus emailVerificationStatus) {

}
