package who.is.neighbor.citizen.domain;

import who.is.neighbor.citizen.infrastructure.jpa.CitizenHobbyEntity;

public interface CitizenHobbyRepository{
    CitizenHobbyEntity save(CitizenHobby citizenHobby);
}
