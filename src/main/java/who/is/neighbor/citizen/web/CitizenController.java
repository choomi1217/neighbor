package who.is.neighbor.citizen.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.citizen.application.CitizenService;
import who.is.neighbor.citizen.web.request.CitizenRegistrationRequest;
import who.is.neighbor.citizen.web.response.CitizenRegistrationResponse;
import who.is.neighbor.citizen.web.response.CitizenResponse;
import who.is.neighbor.hobby.web.request.HobbyRegistrationRequest;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class CitizenController {

    private final CitizenService citizenService;

    public ResponseEntity<CitizenRegistrationResponse> save(@AuthenticationPrincipal UserDetails userDetails,
                                                @Validated @RequestBody CitizenRegistrationRequest citizenRegistrationRequest,
                                                @Validated @RequestBody AddressRegistrationRequest addressRegistrationRequest,
                                                @Validated @RequestBody HobbyRegistrationRequest hobbyRegistrationRequest) throws URISyntaxException {

        CitizenRegistrationResponse response = citizenService.save(
                userDetails.getUsername(),
                citizenRegistrationRequest,
                addressRegistrationRequest,
                hobbyRegistrationRequest);

        return ResponseEntity.created(new URI("/adress-verification")).body(response);
    }

    public ResponseEntity<CitizenResponse> update(@Validated @RequestBody CitizenRegistrationRequest citizenRegistrationRequest) {
        //todo : 시민 수정 처리
        return null;
    }

    public ResponseEntity<CitizenResponse> delete() {
        //todo : 시민 삭제 처리
        return null;
    }


}
