package who.is.neighbor.account.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import who.is.neighbor.account.domain.Account;
import who.is.neighbor.account.domain.AccountEmailVerificationStatus;
import who.is.neighbor.account.domain.AccountRepository;
import who.is.neighbor.account.web.request.AccountUpdateRequest;
import who.is.neighbor.account.web.request.SignUpRequest;
import who.is.neighbor.account.web.response.AccountResponse;
import who.is.neighbor.account.web.response.SignUpResponse;
import who.is.neighbor.citizen.application.CitizenService;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CitizenService citizenService;
    private PasswordEncoder passwordEncoder;

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        Account account = accountRepository.save(
            new Account(
                signUpRequest.email(),
                passwordEncoder.encode(signUpRequest.password())
            )
        );

        return new SignUpResponse(
            account.email()
        );
    }

    public AccountResponse update(String email, AccountUpdateRequest updateRequest) {
        Account account = accountRepository.update(email, updateRequest);
        return new AccountResponse(account.email(), account.emailVerificationStatus(), account.activeStatus());
    }


    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account delete(String email) {
        return accountRepository.delete(email);
    }

    public AccountEmailVerificationStatus emailVerification() {

        return AccountEmailVerificationStatus.VERIFIED;
    }
}
