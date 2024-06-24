package who.is.neighbor.account.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import who.is.neighbor.account.domain.Account;
import who.is.neighbor.account.infrastructure.entity.AccountEntity;
import who.is.neighbor.account.infrastructure.AccountRepository;
import who.is.neighbor.account.web.request.AccountUpdateRequest;
import who.is.neighbor.account.web.request.SignUpRequest;
import who.is.neighbor.account.web.response.AccountResponse;
import who.is.neighbor.account.web.response.SignUpResponse;
import who.is.neighbor.enums.PasswordVerificationStatus;
import who.is.neighbor.mail.EmailSendStatus;
import who.is.neighbor.mail.EmailService;
import who.is.neighbor.mail.JavaEmailService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    @MockBean
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository repository;

    private EmailService emailService = mock(JavaEmailService.class);

    private final String email = "test@naver.com";
    private final String nickname = "nickname";
    private final String password = "password";
    private final String phoneNumber = "010-1234-5678";

    @BeforeEach
    void setUp() {
        accountService = new AccountService(repository, passwordEncoder, emailService);
        passwordEncoder = mock(PasswordEncoder.class);
        repository = mock(AccountRepository.class);
    }

    @Test
    void signUp() {
        Account account = new Account(email, password, nickname, phoneNumber);
        given(repository.save(any(AccountEntity.class))).willReturn(new AccountEntity(account));

        SignUpResponse signUpResponse = accountService.signUp(new SignUpRequest(email, password, nickname, phoneNumber));

        assertThat(signUpResponse).isNotNull();
        assertThat(signUpResponse.email()).isEqualTo(email);
        assertThat(signUpResponse.nickname()).isEqualTo(account.nickname());

    }

    @Test
    void update() {
        Account account = new Account(email, password, nickname, phoneNumber);
        given(repository.save(new AccountEntity(account))).willReturn(new AccountEntity(account));
        given(repository.findByEmail(email)).willReturn(Optional.of(new AccountEntity(account)));

        AccountResponse response = accountService.update(email, new AccountUpdateRequest("update", "999-9999-9999"));

        assertThat(response.nickname()).isEqualTo("update");
    }


    @Test
    void findByEmail() {
        Account account = new Account(email, password, nickname, phoneNumber);
        given(repository.findByEmail(email)).willReturn(Optional.of(new AccountEntity(account)));

        Account findAccount = accountService.findByEmail(email);

        assertThat(findAccount).isNotNull();
        assertThat(findAccount.email()).isEqualTo(email);
        assertThat(findAccount.nickname()).isEqualTo(nickname);
    }

    @Test
    void delete() {
        Account account = new Account(email, password, nickname, phoneNumber);
        given(repository.save(new AccountEntity(account))).willReturn(new AccountEntity(account));
        given(repository.findByEmail(email)).willReturn(Optional.of(new AccountEntity(account)));

        Account deleteAccount = accountService.delete(email);

        assertThat(repository.findByAccountId(deleteAccount.accountId())).isNotNull();
    }

    @Test
    void sendEmailVerification() {
        Account account = new Account(email, password, nickname, phoneNumber);
        given(repository.save(new AccountEntity(account))).willReturn(new AccountEntity(account));
        given(repository.findByEmail(email)).willReturn(Optional.of(new AccountEntity(account)));

        EmailSendStatus emailSendStatus = accountService.sendEmailVerification(email);
        assertThat(emailSendStatus).isEqualTo(EmailSendStatus.SENT);
    }

    @Test
    void passwordVerification() {
        Account account = new Account(email, password, nickname, phoneNumber);
        given(passwordEncoder.matches(password, account.password())).willReturn(true);

        PasswordVerificationStatus passwordVerificationStatus = accountService.passwordVerification(email, password);
        assertThat(passwordVerificationStatus).isEqualTo(PasswordVerificationStatus.VERIFIED);
    }

}