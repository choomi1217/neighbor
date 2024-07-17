package who.is.neighbor.chat.web;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import who.is.neighbor.chat.application.ChatService;
import who.is.neighbor.chat.web.request.MessageRequest;
import who.is.neighbor.chat.web.response.MessageResponse;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void sendMessage(MessageRequest request) {
        MessageResponse chatResponse = chatService.create(request);
        messagingTemplate.convertAndSend("/topic/chat-room/" + chatResponse.roomId(), chatResponse);
    }

}

