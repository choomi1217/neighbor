package who.is.neighbor.citizen.domain;

import who.is.neighbor.citizen.web.response.CitizenResponse;

import java.util.List;

public interface CitizenRepository {
    CitizenResponse save(Citizen citizen);

    List<Citizen> findByCitizenId(Long citizenId);

    Citizen findByAccountId(Long accountId);

    Citizen delete(Long accountId);
}
