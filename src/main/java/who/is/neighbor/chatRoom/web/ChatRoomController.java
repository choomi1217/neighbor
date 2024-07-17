package who.is.neighbor.chatRoom.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import who.is.neighbor.chatRoom.application.ChatRoomService;
import who.is.neighbor.chatRoom.domain.ChatRoom;
import who.is.neighbor.chatRoom.web.request.ChatRoomRequest;

import java.util.List;

@RestController
public class ChatRoomController {

    private final ChatRoomService roomService;

    public ChatRoomController(ChatRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/chatRoom/${citizenId}")
    public ResponseEntity<List<ChatRoom>> chatRoomList(@PathVariable Long citizenId){
        return ResponseEntity.ok(roomService.getMyRooms(citizenId));
    }

    @PostMapping("/chatRoom")
    public ResponseEntity<?> createChatRoom(@RequestBody ChatRoomRequest request){
        roomService.save(request);
        return null;
    }

}
