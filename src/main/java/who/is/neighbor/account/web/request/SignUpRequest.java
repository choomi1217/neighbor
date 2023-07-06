package who.is.neighbor.account.web.request;

public record SignUpRequest (
        String email,
        String password,
        String nickname,
        String phoneNumber
) { }
