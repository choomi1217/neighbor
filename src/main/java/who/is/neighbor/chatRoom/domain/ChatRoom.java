package who.is.neighbor.chatRoom.domain;

import who.is.neighbor.chatRoom.infrastructure.entity.ChatRoomEntity;
import who.is.neighbor.citizen.domain.Citizen;

public record ChatRoom(long id, Citizen citizen, Citizen neighbor) {
    public static ChatRoom from(ChatRoomEntity room) {
        return new ChatRoom(room.getId(), room.getCitizen().toDomain(), room.getNeighbor().toDomain())  ;
    }
}
