package who.is.neighbor.account.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest (
        @NotNull @Email
        String email,
        @NotNull
        String password,
        @NotNull
        String nickname,
        String phoneNumber
) { }
