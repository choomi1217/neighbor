package who.is.neighbor.account.application;

import jakarta.mail.SendFailedException;
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
import who.is.neighbor.mail.EmailMessage;
import who.is.neighbor.mail.EmailSendStatus;
import who.is.neighbor.mail.EmailService;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;

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

    public EmailSendStatus sendEmailVerification(String email) {
        Account account = accountRepository.findByEmail(email);

        String message = EmailMessage.defaultMessage(account.email());
        EmailMessage emailMessage = EmailMessage.of(account.email(), message);

        try {
            emailService.send(emailMessage);
        } catch (SendFailedException e) {
            return EmailSendStatus.FAILED;
        }

        return EmailSendStatus.SENT;
    }

    public AccountResponse emailVerification(String token, String email) {
        Account account = accountRepository.findByEmail(email);
        if (account.emailVerificationStatus()== AccountEmailVerificationStatus.VERIFIED) {
            throw new IllegalArgumentException("Already Verified");
        }
        if(token.equals(account.emailVerificationToken())) {
            Account verified = account.verifyEmail();
            accountRepository.update(verified);
            return new AccountResponse(verified.email(), verified.emailVerificationStatus(), verified.activeStatus());
        }else{
            throw new IllegalArgumentException("Invalid Token");
        }
    }
}
