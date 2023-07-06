package who.is.neighbor.account.web.response;

public record AccountResponse(String email, String phoneNumber, boolean isEmailVerified, boolean isActive){
}
