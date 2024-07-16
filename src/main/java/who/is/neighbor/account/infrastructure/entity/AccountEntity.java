package who.is.neighbor.account.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import who.is.neighbor.enums.AccountActiveStatus;
import who.is.neighbor.enums.AccountEmailVerificationStatus;
import who.is.neighbor.account.domain.Account;
import who.is.neighbor.account.web.request.AccountUpdateRequest;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long accountId;

    private String email;

    private String password;

    private String nickname;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private AccountEmailVerificationStatus emailVerificationStatus;

    @Enumerated(EnumType.STRING)
    private AccountActiveStatus activeStatus;

    @OneToMany(mappedBy = "accountEntity")
    private List<CitizenEntity> citizens = new ArrayList<>();

    private String emailVerificationToken;

    public AccountEntity() {
    }

    public AccountEntity(Account account) {
        this.email = account.email();
        this.password = account.password();
        this.nickname = account.nickname();
        this.phoneNumber = account.phoneNumber();
        this.emailVerificationStatus = account.emailVerificationStatus();
        this.activeStatus = account.activeStatus();
        emailVerificationToken = account.emailVerificationToken();
    }

    public Account toDomain() {
        return new Account(this.accountId, this.email, this.password, this.nickname, this.phoneNumber, this.emailVerificationToken, this.emailVerificationStatus, this.activeStatus);
    }

    public AccountEntity update(AccountUpdateRequest updateRequest) {
        this.nickname = updateRequest.nickname();
        this.phoneNumber = updateRequest.phoneNumber();
        return this;
    }

    public void update(Account account) {
        this.email = account.email();
        this.password = account.password();
        this.nickname = account.nickname();
        this.phoneNumber = account.phoneNumber();
        this.emailVerificationStatus = account.emailVerificationStatus();
        this.activeStatus = account.activeStatus();
        emailVerificationToken = account.emailVerificationToken();
    }

    public void delete() {
        this.activeStatus = AccountActiveStatus.DELETED;
    }

    public boolean isEmailVerified() {
        return this.emailVerificationStatus == AccountEmailVerificationStatus.VERIFIED;
    }
}
