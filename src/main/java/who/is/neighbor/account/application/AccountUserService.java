package who.is.neighbor.account.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import who.is.neighbor.account.domain.Account;
import who.is.neighbor.account.domain.AccountEmailVerificationStatus;
import who.is.neighbor.account.domain.AccountRepository;
import who.is.neighbor.account.web.request.LoginRequest;
import who.is.neighbor.account.web.response.LoginResponse;
import who.is.neighbor.citizen.application.CitizenService;
import who.is.neighbor.citizen.domain.Citizen;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountUserService {

    //private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CitizenService citizenService;
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {

        Account account = accountService.findByEmail(loginRequest.email());
        List<Citizen> userList = null;

        if(!passwordEncoder.matches(loginRequest.password(), account.password())){
            throw new IllegalArgumentException("Password is not matched");
        }
        if(AccountEmailVerificationStatus.VERIFIED.toString().equals(account.emailVerificationStatus())){
            userList = citizenService.findUserByAccountId(account.accountId());
        }

        return new LoginResponse( account.email(), userList, account.emailVerificationStatus());
    }

    public void delete(String email) {
        Account deletedAccount = accountService.delete(email);
        Citizen deletedCitizen = citizenService.delete(deletedAccount.accountId());
    }
}
