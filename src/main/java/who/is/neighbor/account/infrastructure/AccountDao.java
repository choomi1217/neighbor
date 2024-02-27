package who.is.neighbor.account.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import who.is.neighbor.account.domain.Account;
import who.is.neighbor.account.domain.AccountRepository;
import who.is.neighbor.account.infrastructure.jpa.AccountEntity;
import who.is.neighbor.account.infrastructure.jpa.AccountJpaRepository;
import who.is.neighbor.account.web.request.AccountUpdateRequest;

@Repository
@RequiredArgsConstructor
public class AccountDao implements AccountRepository {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = accountJpaRepository.save(new AccountEntity(account));
        return accountEntity.toDomain();
    }

    @Override
    public Account findByEmail(String email) {
        AccountEntity accountEntity = accountJpaRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("계정이 존재하지 않습니다."));
        return accountEntity.toDomain();
    }

    @Override
    public Account update(String email, AccountUpdateRequest updateRequest) {
        return accountJpaRepository.findByEmail(email).
                map(accountEntity -> {
                    accountEntity.update(updateRequest);
                    return accountEntity;
                }).orElseThrow(() -> new RuntimeException("계정이 존재하지 않습니다.")).toDomain();
    }

    @Override
    public Account delete(String email) {
        AccountEntity accountEntity = accountJpaRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("계정이 존재하지 않습니다."));
        accountEntity.delete();
        return accountEntity.toDomain();
    }

    @Override
    public void update(Account account) {
        accountJpaRepository.findByEmail(account.email()).ifPresent(accountEntity -> {
            accountEntity.update(account);
            accountJpaRepository.save(accountEntity);
            accountEntity.toDomain();
        });
    }
}
