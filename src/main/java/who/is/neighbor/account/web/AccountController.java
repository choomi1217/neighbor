package who.is.neighbor.account.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

@Tag(name = "Account APi")
@RestController
@RequiredArgsConstructor
public class AccountController {

    AccountService accountService;
    AccountUserService accountUserService;

    @Operation(summary = "회원가입")
    @PostMapping("/accounts")
    public ResponseEntity<SignUpResponse> registration(@Validated @RequestBody SignUpRequest signUpRequest) throws URISyntaxException {
        SignUpResponse signUpResponse = accountService.signUp(signUpRequest);
        return ResponseEntity.ok(signUpResponse);
    }

    @Operation(summary = "회원정보 수정")
    @PutMapping("/accounts")
    public ResponseEntity<AccountResponse> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @Validated @RequestBody AccountUpdateRequest updateRequest) throws URISyntaxException {
        AccountResponse accountResponse = accountService.update(userDetails.getUsername(), updateRequest);
        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping("/accounts")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        accountUserService.delete(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그인 페이지")
    @GetMapping("/accounts/login")
    public ResponseEntity<Void> loginPage() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:3000/login"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @Operation(summary = "로그인")
    @PostMapping("/accounts/login")
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest loginRequest) throws URISyntaxException {
        return ResponseEntity.ok(accountUserService.login(loginRequest));
    }

    @Operation(summary = "이메일 인증 메일 전송")
    @GetMapping("/accounts/{email}/email-verification")
    public ResponseEntity<EmailSendStatus> sendEmailVerification(@PathVariable String email) {
        EmailSendStatus status = accountService.sendEmailVerification(email);
        return ResponseEntity.ok(status);
    }

    @Operation(summary = "이메일 인증")
    @GetMapping("/accounts/email-verification")
    public ResponseEntity<AccountResponse> emailVerification(String token, String email) {
        AccountResponse accountResponse = accountService.emailVerification(token, email);
        return ResponseEntity.ok(accountResponse);
    }

}
