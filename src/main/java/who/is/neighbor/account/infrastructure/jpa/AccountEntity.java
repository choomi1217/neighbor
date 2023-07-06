package who.is.neighbor.account.infrastructure.jpa;

import jakarta.persistence.*;
import who.is.neighbor.account.domain.Account;
import who.is.neighbor.account.web.request.AccountUpdateRequest;
import who.is.neighbor.citizen.infrastructure.jpa.CitizenEntity;

@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long accountId;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean isEmailVerified;
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private CitizenEntity userEntity;

    public AccountEntity() {}

    public AccountEntity(String email, String password, String phoneNumber, boolean isEmailVerified, boolean isActive, CitizenEntity userEntity) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isEmailVerified = isEmailVerified;
        this.isActive = isActive;
        this.userEntity = userEntity;
    }

    public AccountEntity(Account account) {
        this.email = account.email();
        this.password = account.password();
        this.phoneNumber = account.phoneNumber();
        this.isEmailVerified = account.isEmailVerified();
        this.isActive = account.isActive();
    }

    public Account toDomain() {
        return new Account(this.accountId ,email, password, phoneNumber, isEmailVerified, isActive);
    }

    public void update(AccountUpdateRequest updateRequest) {
        this.email = updateRequest.email();
        this.phoneNumber = updateRequest.phoneNumber();
    }
}
