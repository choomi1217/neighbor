package who.is.neighbor.chat.application;

import org.springframework.stereotype.Service;
import who.is.neighbor.chat.dto.Chat;
import who.is.neighbor.chat.infarstructure.ChatJpaRepository;
import who.is.neighbor.chat.infarstructure.entity.ChatEntity;
import who.is.neighbor.chat.web.request.MessageRequest;
import who.is.neighbor.chat.web.response.MessageResponse;
import who.is.neighbor.chatRoom.infrastructure.ChatRoomJpaRepository;
import who.is.neighbor.chatRoom.infrastructure.entity.ChatRoomEntity;
import who.is.neighbor.citizen.infrastructure.CitizenJpaRepository;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;

@Service
public class ChatService {

    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final CitizenJpaRepository citizenJpaRepository;
    private final ChatJpaRepository chatJpaRepository;

    public ChatService(ChatRoomJpaRepository chatRoomJpaRepository, CitizenJpaRepository citizenJpaRepository, ChatJpaRepository chatJpaRepository) {
        this.chatRoomJpaRepository = chatRoomJpaRepository;
        this.citizenJpaRepository = citizenJpaRepository;
        this.chatJpaRepository = chatJpaRepository;
    }

    public MessageResponse create(MessageRequest request) {
        ChatRoomEntity chatRoomEntity = chatRoomJpaRepository.findById(request.roomId()).get();
        CitizenEntity citizen = citizenJpaRepository.findByCitizenId(request.citizenId());
        String message = request.message();


        ChatEntity chatEntity = chatJpaRepository.save(ChatEntity.from(chatRoomEntity, citizen, message));
        return MessageResponse.of(Chat.of(chatEntity));
    }
}
