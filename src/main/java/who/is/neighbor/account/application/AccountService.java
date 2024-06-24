package who.is.neighbor.account.application;

import jakarta.mail.SendFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import who.is.neighbor.account.domain.Account;
import who.is.neighbor.account.infrastructure.entity.AccountEntity;
import who.is.neighbor.account.infrastructure.AccountRepository;
import who.is.neighbor.account.web.request.AccountUpdateRequest;
import who.is.neighbor.account.web.request.SignUpRequest;
import who.is.neighbor.account.web.response.AccountResponse;
import who.is.neighbor.account.web.response.SignUpResponse;
import who.is.neighbor.enums.AccountEmailVerificationStatus;
import who.is.neighbor.enums.PasswordVerificationStatus;
import who.is.neighbor.mail.EmailMessage;
import who.is.neighbor.mail.EmailSendStatus;
import who.is.neighbor.mail.EmailService;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public AccountService(AccountRepository repository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        Account account = new Account(
                signUpRequest.email(),
                passwordEncoder.encode(signUpRequest.password()),
                signUpRequest.nickname(),
                signUpRequest.phoneNumber()
        );
        AccountEntity accountEntity = repository.save(new AccountEntity(account));
        Account domain = accountEntity.toDomain();
        return new SignUpResponse(
                domain.email(),
                domain.nickname(),
                domain.phoneNumber()
        );
    }

    @Transactional
    public AccountResponse update(String email, AccountUpdateRequest updateRequest) {
        AccountEntity accountEntity = repository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );
        AccountEntity updated = accountEntity.update(updateRequest);
        Account domain = updated.toDomain();
        return new AccountResponse(domain.email(), domain.nickname(), domain.emailVerificationStatus(), domain.activeStatus());
    }


    public Account findByEmail(String email) {
        AccountEntity accountEntity = repository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );
        return accountEntity.toDomain();
    }

    public Account delete(String email) {
        AccountEntity accountEntity = repository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );
        repository.delete(accountEntity);
        return accountEntity.toDomain();
    }

    public EmailSendStatus sendEmailVerification(String email) {
        AccountEntity accountEntity = repository.findByEmail(email).orElseThrow();
        Account account = accountEntity.toDomain();

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
        AccountEntity accountEntity = repository.findByEmail(email).orElseThrow();
        Account account = accountEntity.toDomain();
        if (account.emailVerificationStatus() == AccountEmailVerificationStatus.VERIFIED) {
            throw new IllegalArgumentException("Already Verified");
        }
        if (token.equals(account.emailVerificationToken())) {
            Account verified = account.verifyEmail();
            return new AccountResponse(verified.email(), verified.nickname(), verified.emailVerificationStatus(), verified.activeStatus());
        } else {
            throw new IllegalArgumentException("Invalid Token");
        }
    }

    public PasswordVerificationStatus passwordVerification(String email, String password) {
        AccountEntity accountEntity = repository.findByEmail(email).orElseThrow();
        Account account = accountEntity.toDomain();
        if (!passwordEncoder.matches(password, account.password())) {
            return PasswordVerificationStatus.NON_VERIFIED;
        }
        return PasswordVerificationStatus.VERIFIED;
    }
}
