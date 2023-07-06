package who.is.neighbor.citizen.domain;

import java.util.List;

public interface CitizenRepository {
    Citizen save(Citizen citizen);

    List<Citizen> findByCitizenId(Long citizenId);
}
