package who.is.neighbor.chat.dto;

import who.is.neighbor.chat.infarstructure.entity.ChatEntity;

public record Chat(long roomId, long citizenId, String citizenName, String message) {
    public static Chat of(ChatEntity chatEntity) {
        return new Chat(chatEntity.getChatRoom().getId(),
                chatEntity.getCitizen().getCitizenId(),
                chatEntity.getCitizen().getNickname(),
                chatEntity.getMessage());
    }
}
