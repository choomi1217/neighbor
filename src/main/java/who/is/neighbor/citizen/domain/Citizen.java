package who.is.neighbor.citizen.domain;

import who.is.neighbor.address.domain.Address;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;
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
    public static Citizen from(CitizenEntity entity) {
        return new Citizen(
                entity.getNickname(),
                entity.getCreatedAt(),
                entity.getPhoneNumber(),
                entity.getAddressEntities().stream()
                        .map(Address::from)
                        .toList(),
                entity.getHobbyEntities().stream()
                        .map(hobbyEntity -> Hobby.from(hobbyEntity.getHobby().getHobby()))
                        .toList()
        );
    }
}
