package who.is.neighbor.address.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import who.is.neighbor.address.application.AddressService;
import who.is.neighbor.address.domain.EupMyeonDong;
import who.is.neighbor.address.domain.SiDo;
import who.is.neighbor.address.domain.SiGunGu;
import who.is.neighbor.address.web.request.Coordinates;
import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.address.web.response.AddressVerifiedResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

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

    @GetMapping("/address/sido")
    public ResponseEntity<List<SiDo>> getSiDo(){
        List<SiDo> siDoList = addressService.getSiDoList();
        return ResponseEntity.ok(siDoList);
    }

    @GetMapping("/address/sigungu")
    public ResponseEntity<List<SiGunGu>> getSiGunGu(String sido){
        List<SiGunGu> siGunGuList = addressService.getSiGunGuList(sido);
        return ResponseEntity.ok(siGunGuList);
    }

    @GetMapping("/address/eupmyeondong")
    public ResponseEntity<List<EupMyeonDong>> getEupMyeonDong(String sido, String sigungu){
        List<EupMyeonDong> eupMyeonDongList = addressService.getEupMyeonDongList(sido, sigungu);
        return ResponseEntity.ok(eupMyeonDongList);
    }

}
