package who.is.neighbor.address.domain;

import who.is.neighbor.address.infrastructure.jpa.AddressEntity;
import who.is.neighbor.address.web.request.AddressUpdateRequest;
import who.is.neighbor.address.web.response.AddressResponse;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    AddressResponse save(Address address);

    List<SiDo> getSiDoList();

    List<SiGunGu> getSiGunGuList(String sido);

    List<EupMyeonDong> getEupMyeonDongList(String sido, String sigungu);

    void update(Long addressId, AddressUpdateRequest request);

    Address findById(Long addressId);
}
