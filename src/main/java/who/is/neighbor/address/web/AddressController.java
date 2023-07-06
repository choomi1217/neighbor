package who.is.neighbor.address.web;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import who.is.neighbor.address.web.request.AddressSearchRequest;
import who.is.neighbor.address.web.request.Coordinates;
import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.address.web.response.AddressVerifiedResponse;

@RestController
public class AddressController {

    @PostMapping("/address")
    public ResponseEntity<AddressResponse> registration() {
        //todo : 주소 등록 처리
        return null;
    }

    @PutMapping("/address")
    public ResponseEntity<AddressResponse> update() {
        //todo : 주소 수정 처리
        return null;
    }

    @DeleteMapping("/address")
    public ResponseEntity<Void> delete() {
        //todo : 주소 삭제 처리
        return null;
    }

    @PostMapping("/address-verification/{coordinates}")
    public ResponseEntity<AddressVerifiedResponse> addressVerification(@PathVariable("coordinates") Coordinates coordinates) {
        //todo : 주소 검증 처리
        return null;
    }

    @GetMapping("/address")
    public ResponseEntity<AddressResponse> getAddress(@Validated @RequestBody AddressSearchRequest addressSearchRequest){
        //todo : 주소 조회 처리
        return null;
    }

}
