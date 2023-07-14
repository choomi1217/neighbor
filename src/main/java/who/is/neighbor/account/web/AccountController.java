package who.is.neighbor.account.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import who.is.neighbor.account.application.AccountService;
import who.is.neighbor.account.application.AccountUserService;
import who.is.neighbor.account.web.request.AccountUpdateRequest;
import who.is.neighbor.account.web.request.LoginRequest;
import who.is.neighbor.account.web.request.SignUpRequest;
import who.is.neighbor.account.web.response.AccountResponse;
import who.is.neighbor.account.web.response.LoginResponse;
import who.is.neighbor.account.web.response.SignUpResponse;
import who.is.neighbor.mail.EmailSendStatus;

import java.net.URI;
import java.net.URISyntaxException;

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
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/accounts")
    public ResponseEntity<Void> deleteAccount( @AuthenticationPrincipal UserDetails userDetails) {
        accountUserService.delete(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<AccountResponse> getAccount() {
        //todo : 계정 조회 처리
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accounts/login")
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest loginRequest) throws URISyntaxException {
        return ResponseEntity.ok(accountUserService.login(loginRequest));
    }

    @PostMapping("/accounts/logout")
    public ResponseEntity<Void> logout() {
        //todo : 로그아웃 처리
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/accounts/{email}/email-verification")
    public ResponseEntity<EmailSendStatus> emailVerification(@PathVariable String email) {
        EmailSendStatus status = accountService.emailVerification(email);
        return ResponseEntity.ok(status);
    }

    public ResponseEntity<Void> passwordVerification() {
        //todo : 비밀번호 인증 처리
        return ResponseEntity.ok().build();
    }
}
