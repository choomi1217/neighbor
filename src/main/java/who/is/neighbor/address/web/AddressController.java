package who.is.neighbor.address.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import who.is.neighbor.address.application.AddressService;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.request.AddressUpdateRequest;
import who.is.neighbor.address.web.request.Coordinates;
import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.address.web.response.AddressVerifiedResponse;
import who.is.neighbor.enums.AddressVerified;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<AddressResponse> registration(@Validated AddressRegistrationRequest request) throws URISyntaxException {
        return ResponseEntity.created(new URI("/address")).body(addressService.save(request));
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<AddressResponse> update(@PathVariable Long addressId, AddressUpdateRequest request) {
        return ResponseEntity.ok(addressService.update(addressId, request));
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<Void> delete(@PathVariable Long addressId) {
        addressService.delete(addressId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/address-verification/{addressId}")
    public ResponseEntity<AddressVerified> addressVerification(@PathVariable Long addressId, @Validated Coordinates coordinates) {
        if (addressService.addressVerification(addressId, coordinates)) {
            return ResponseEntity.ok(AddressVerified.VERIFIED);
        }else {
            return ResponseEntity.ok(AddressVerified.NON_VERIFIED);
        }
    }

//    @GetMapping("/address/sido")
//    public ResponseEntity<List<SiDo>> getSiDo(){
//        List<SiDo> siDoList = addressServi]ce.getSiDoList();
//        return ResponseEntity.ok(siDoList);
//    }
//
//    @GetMapping("/address/sigungu")
//    public ResponseEntity<List<SiGunGu>> getSiGunGu(String sido){
//        List<SiGunGu> siGunGuList = addressService.getSiGunGuList(sido);
//        return ResponseEntity.ok(siGunGuList);
//    }
//
//    @GetMapping("/address/eupmyeondong")
//    public ResponseEntity<List<EupMyeonDong>> getEupMyeonDong(String sido, String sigungu){
//        List<EupMyeonDong> eupMyeonDongList = addressService.getEupMyeonDongList(sido, sigungu);
//        return ResponseEntity.ok(eupMyeonDongList);
//    }

}
