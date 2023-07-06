package who.is.neighbor.citizen.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import who.is.neighbor.citizen.web.request.CitizenRegistrationRequest;
import who.is.neighbor.citizen.web.response.CitizenResponse;

@RestController
@RequiredArgsConstructor
public class CitizenController {

    public ResponseEntity<CitizenResponse> save(@Validated @RequestBody CitizenRegistrationRequest citizenRegistrationRequest) {
        //todo : 시민 등록 처리
        return null;
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
