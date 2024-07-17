package who.is.neighbor.chat.web.request;

public record MessageRequest (long roomId ,long citizenId, String message){
}
