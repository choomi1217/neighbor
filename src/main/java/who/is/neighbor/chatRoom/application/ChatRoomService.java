package who.is.neighbor.chatRoom.application;

import org.springframework.stereotype.Service;
import who.is.neighbor.chatRoom.domain.ChatRoom;
import who.is.neighbor.chatRoom.infrastructure.ChatRoomJpaRepository;
import who.is.neighbor.chatRoom.infrastructure.entity.ChatRoomEntity;
import who.is.neighbor.chatRoom.web.request.ChatRoomRequest;
import who.is.neighbor.citizen.infrastructure.CitizenJpaRepository;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ChatRoomService {

    private final CitizenJpaRepository citizenJpaRepository;
    private final ChatRoomJpaRepository roomJpaRepository;

    public ChatRoomService(CitizenJpaRepository citizenJpaRepository, ChatRoomJpaRepository roomJpaRepository) {
        this.citizenJpaRepository = citizenJpaRepository;
        this.roomJpaRepository = roomJpaRepository;
    }

    public List<ChatRoom> getMyRooms(Long citizenId) {
        CitizenEntity citizen = citizenJpaRepository.findByCitizenId(citizenId);
        List<ChatRoomEntity> citizenChatRooms = roomJpaRepository.findByCitizen(citizen);
        List<ChatRoomEntity> neighborChatRooms = roomJpaRepository.findByNeighbor(citizen);
        return Stream.concat(citizenChatRooms.stream().map(ChatRoom::from),
                neighborChatRooms.stream().map(ChatRoom::from))
                .toList();
    }

    public ChatRoom save(ChatRoomRequest request) {
        CitizenEntity citizen = citizenJpaRepository.findByCitizenId(request.citizenId());
        CitizenEntity neighbor = citizenJpaRepository.findByCitizenId(request.neighborId());
        Optional<ChatRoomEntity> roomEntity = roomJpaRepository.findByCitizenAndNeighbor(citizen, neighbor);
        return roomEntity.map(ChatRoom::from).orElseGet(() -> create(citizen, neighbor));
    }

    private ChatRoom create(CitizenEntity citizen, CitizenEntity neighbor) {
        ChatRoomEntity roomEntity = roomJpaRepository.save(ChatRoomEntity.of(citizen, neighbor));
        return ChatRoom.from(roomEntity);
    }

}
