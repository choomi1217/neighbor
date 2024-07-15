package who.is.neighbor.address.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import who.is.neighbor.address.application.AddressService;
import who.is.neighbor.address.domain.Eupmyeondong;
import who.is.neighbor.address.domain.Sido;
import who.is.neighbor.address.domain.Sigungu;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.request.AddressUpdateRequest;
import who.is.neighbor.address.web.request.Coordinates;
import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.enums.AddressVerified;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
        } else {
            return ResponseEntity.ok(AddressVerified.NON_VERIFIED);
        }
    }

    @GetMapping("/address/sido")
    public ResponseEntity<List<Sido>> getSiDo(){
        List<Sido> sidoList = addressService.findSidos();
        return ResponseEntity.ok(sidoList);
    }

    @GetMapping("/address/sigungu")
    public ResponseEntity<List<Sigungu>> getSiGunGu(String sido){
        List<Sigungu> siGunGuList = addressService.findSigungus(sido);
        return ResponseEntity.ok(siGunGuList);
    }

    @GetMapping("/address/eupmyeondong")
    public ResponseEntity<List<Eupmyeondong>> getEupMyeonDong(String sido, String sigungu){
        List<Eupmyeondong> eupMyeonDongList = addressService.findEupmyeondongs(sido, sigungu);
        return ResponseEntity.ok(eupMyeonDongList);
    }

}
