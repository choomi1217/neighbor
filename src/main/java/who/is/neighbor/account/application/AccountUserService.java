package who.is.neighbor.account.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import who.is.neighbor.account.domain.Account;
import who.is.neighbor.account.web.request.LoginRequest;
import who.is.neighbor.account.web.response.LoginResponse;
import who.is.neighbor.citizen.application.CitizenService;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.enums.AccountEmailVerificationStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountUserService {
    private final AccountService accountService;
    private final CitizenService citizenService;
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {

        Account account = accountService.findByEmail(loginRequest.email());
        List<Citizen> userList = new ArrayList<>();

        if (!passwordEncoder.matches(loginRequest.password(), account.password())) {
            throw new IllegalArgumentException("Password is not matched");
        }
        if (AccountEmailVerificationStatus.VERIFIED == account.emailVerificationStatus()) {
            userList = citizenService.findUserByAccountId(account.accountId());
        }

        return new LoginResponse(account.email(), userList, account.emailVerificationStatus());
    }

    public void delete(String email) {
        Account deletedAccount = accountService.delete(email);
        citizenService.delete(deletedAccount.accountId());
    }
}
