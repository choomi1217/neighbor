package who.is.neighbor.account.domain;

import who.is.neighbor.account.web.request.AccountUpdateRequest;

public interface AccountRepository {
    Account save(Account account);

    Account findByEmail(String email);

    Account update(String email, AccountUpdateRequest updateRequest);

    Account delete(String email);

    void update(Account verified);
}
