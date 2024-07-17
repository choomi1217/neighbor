package who.is.neighbor.chatRoom.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CitizenEntity citizen;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CitizenEntity neighbor;

    public ChatRoomEntity(CitizenEntity citizen, CitizenEntity neighbor) {
        this.citizen = citizen;
        this.neighbor = neighbor;
    }

    public static ChatRoomEntity of(CitizenEntity citizen, CitizenEntity neighbor) {
        return new ChatRoomEntity(citizen, neighbor);
    }
}
