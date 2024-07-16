package who.is.neighbor.citizen.domain;

import who.is.neighbor.address.domain.Address;
import who.is.neighbor.hobby.domain.Hobby;

import java.time.LocalDate;
import java.util.List;

public record Citizen(
        String nickname,
        LocalDate createdAt,
        String phoneNumber,
        List<Address> address,
        List<Hobby> hobby
) {
}
