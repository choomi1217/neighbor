package who.is.neighbor.address.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import who.is.neighbor.address.domain.Address;
import who.is.neighbor.address.infrastructure.AddressRepository;
import who.is.neighbor.address.infrastructure.entity.AddressEntity;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.request.AddressUpdateRequest;
import who.is.neighbor.address.web.response.AddressResponse;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressResponse save(AddressRegistrationRequest request) {
        Address address = new Address(request);
        AddressEntity save = addressRepository.save(new AddressEntity(address));
        return save.toDomain();
    }

//    public List<SiDo> getSiDoList() {
//        return addressRepository.getSiDoList();
//    }
//
//    public List<SiGunGu> getSiGunGuList(String sido) {
//        return addressRepository.getSiGunGuList(sido);
//    }
//
//    public List<EupMyeonDong> getEupMyeonDongList(String sido, String sigungu) {
//        return addressRepository.getEupMyeonDongList(sido, sigungu);
//    }

    public void update(Long addressId, AddressUpdateRequest request) {
        AddressEntity addressEntity = addressRepository.findById(addressId).orElseThrow();
        addressEntity.update(request);
    }
}
