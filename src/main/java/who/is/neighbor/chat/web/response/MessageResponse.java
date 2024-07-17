package who.is.neighbor.chat.web.response;

import who.is.neighbor.chat.dto.Chat;

import java.time.LocalDateTime;

public record MessageResponse(Long roomId, String message, String neighborName, LocalDateTime createAt) {
    public static MessageResponse of(Chat chat) {
        return new MessageResponse(
                chat.roomId(),
                chat.message(),
                chat.citizenName(),
                LocalDateTime.now()
        );
    }
}
