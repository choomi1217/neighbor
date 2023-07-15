package who.is.neighbor.account.infrastructure.jpa;

import jakarta.persistence.*;
import who.is.neighbor.account.domain.Account;
import who.is.neighbor.account.application.AccountActiveStatus;
import who.is.neighbor.account.application.AccountEmailVerificationStatus;
import who.is.neighbor.account.web.request.AccountUpdateRequest;
import who.is.neighbor.citizen.infrastructure.jpa.CitizenEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long accountId;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountEmailVerificationStatus emailVerificationStatus;
    @Enumerated(EnumType.STRING)
    private AccountActiveStatus activeStatus;
    @OneToMany(mappedBy = "accountEntity")
    private List<CitizenEntity> citizens = new ArrayList<>();

    private String emailVerificationToken;

    public AccountEntity() {}

    public AccountEntity(String email, String password, AccountEmailVerificationStatus emailVerificationStatus, AccountActiveStatus activeStatus) {
        this.email = email;
        this.password = password;
        this.emailVerificationStatus = emailVerificationStatus;
        this.activeStatus = activeStatus;
    }

    public AccountEntity(Account account) {
        this.email = account.email();
        this.password = account.password();
        this.emailVerificationStatus = account.emailVerificationStatus();
        this.activeStatus = account.activeStatus();
        emailVerificationToken = account.emailVerificationToken();
    }

    public Account toDomain() {
        return new Account(this.accountId ,this.email, this.password, this.emailVerificationToken, this.emailVerificationStatus, this.activeStatus);
    }

    public void update(AccountUpdateRequest updateRequest) {
        this.email = updateRequest.email();
    }

    public void update(Account account) {
        this.email = account.email();
        this.password = account.password();
        this.emailVerificationStatus = account.emailVerificationStatus();
        this.activeStatus = account.activeStatus();
        emailVerificationToken = account.emailVerificationToken();
    }

    public void delete() {
        this.activeStatus = AccountActiveStatus.DELETED;
    }
}
