package who.is.neighbor.address.domain;

import who.is.neighbor.address.web.response.AddressResponse;

import java.util.List;

public interface AddressRepository {
    AddressResponse save(Address address);

    List<SiDo> getSiDoList();

    List<SiGunGu> getSiGunGuList(String sido);

    List<EupMyeonDong> getEupMyeonDongList(String sido, String sigungu);
}
