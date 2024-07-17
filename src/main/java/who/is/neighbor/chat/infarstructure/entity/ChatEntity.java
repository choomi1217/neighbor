package who.is.neighbor.chat.infarstructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import who.is.neighbor.chatRoom.infrastructure.entity.ChatRoomEntity;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;

@NoArgsConstructor
@Getter
@Entity
public class ChatEntity {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ChatRoomEntity chatRoom;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CitizenEntity citizen;

    private String message;

    public ChatEntity(ChatRoomEntity chatRoomEntity, CitizenEntity citizen, String message) {
        this.chatRoom = chatRoomEntity;
        this.citizen = citizen;
        this.message = message;
    }

    public static ChatEntity from(ChatRoomEntity chatRoomEntity, CitizenEntity citizen, String message) {
        return new ChatEntity(chatRoomEntity, citizen, message);
    }
}
