package who.is.neighbor.citizen.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.citizen.domain.CitizenRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CitizenService {
    private final CitizenRepository citizenRepository;
    public Citizen save(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    public List<Citizen> findUserByAccountId(Long citizenId) {
        return citizenRepository.findByCitizenId(citizenId);
    }

    public Citizen delete(Long accountId) {
        return citizenRepository.delete(accountId);
    }
}
