package who.is.neighbor.account.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import who.is.neighbor.account.application.AccountService;
import who.is.neighbor.account.application.AccountUserService;
import who.is.neighbor.account.application.PasswordVerificationStatus;
import who.is.neighbor.account.web.request.AccountUpdateRequest;
import who.is.neighbor.account.web.request.LoginRequest;
import who.is.neighbor.account.web.request.SignUpRequest;
import who.is.neighbor.account.web.response.AccountResponse;
import who.is.neighbor.account.web.response.LoginResponse;
import who.is.neighbor.account.web.response.SignUpResponse;
import who.is.neighbor.mail.EmailSendStatus;

import java.net.URI;
import java.net.URISyntaxException;

@Tag(name = "Account APi")
@RestController
@RequiredArgsConstructor
public class AccountController {

    AccountService accountService;
    AccountUserService accountUserService;

    @PostMapping("/accounts")
    public ResponseEntity<SignUpResponse> registration(@Validated @RequestBody SignUpRequest signUpRequest) throws URISyntaxException {
        return ResponseEntity.created(new URI("/accounts/login")).body(accountService.signUp(signUpRequest));
    }


    @PutMapping("/accounts")
    public ResponseEntity<AccountResponse> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @Validated @RequestBody AccountUpdateRequest updateRequest) throws URISyntaxException {
        AccountResponse accountResponse = accountService.update(userDetails.getUsername(), updateRequest);
        return ResponseEntity.ok(accountResponse);
    }

    @DeleteMapping("/accounts")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        accountUserService.delete(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accounts/login")
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest loginRequest) throws URISyntaxException {
        return ResponseEntity.ok(accountUserService.login(loginRequest));
    }

    @GetMapping("/accounts/{email}/email-verification")
    public ResponseEntity<EmailSendStatus> sendEmailVerification(@PathVariable String email) {
        EmailSendStatus status = accountService.sendEmailVerification(email);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/accounts/email-verification")
    public ResponseEntity<AccountResponse> emailVerification(String token, String email) {
        AccountResponse accountResponse = accountService.emailVerification(token, email);
        return ResponseEntity.ok(accountResponse);
    }

    @PostMapping("/accounts/password-verification")
    public ResponseEntity<PasswordVerificationStatus> passwordVerification(@AuthenticationPrincipal UserDetails userDetails, String password) {
        PasswordVerificationStatus verificationStatus = accountService.passwordVerification(userDetails.getUsername(), password);
        return ResponseEntity.ok(verificationStatus);
    }
}
