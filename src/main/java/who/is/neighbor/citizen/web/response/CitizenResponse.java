package who.is.neighbor.citizen.web.response;

import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.hobby.web.response.HobbyResponse;

import java.time.LocalDate;

public record CitizenResponse(
        String nickname,
        String phoneNumber,
        LocalDate createdAt
) {
    public CitizenResponse(Citizen domain) {
        this(domain.nickname(),
                domain.phoneNumber(),
                domain.createdAt()
        );
    }
}
