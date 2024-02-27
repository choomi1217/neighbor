package who.is.neighbor.address.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import who.is.neighbor.address.domain.*;
import who.is.neighbor.address.infrastructure.jpa.*;
import who.is.neighbor.address.web.response.AddressResponse;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AddressDao implements AddressRepository {
    private final AddressJpaRepository addressJpaRepository;
    private final SiDoJpaRepository siDoJpaRepository;
    private final SigunguJpaRepository sigunguJpaRepository;
    private final EupMyeonDongJpaRepository eupMyeonDongJpaRepository;
    @Override
    public AddressResponse save(Address address) {
        SiDoEntity siDoEntity = siDoJpaRepository.findBySiDoName(address.sido().sidoName());
        SiGunGuEntity siGunGuEntity = sigunguJpaRepository.findBySiDoAndSiGunGuName(siDoEntity, address.sigungu().sigunguName());
        AddressEntity addressEntity = addressJpaRepository.save(new AddressEntity());
        return addressEntity.toDomain();
    }

    @Override
    public List<SiDo> getSiDoList() {
        List<SiDoEntity> all = siDoJpaRepository.findAll();
        return all.stream().map(SiDoEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<SiGunGu> getSiGunGuList(String sido) {
        SiDoEntity siDoEntity = siDoJpaRepository.findBySiDoName(sido);
        List<SiGunGuEntity> all = sigunguJpaRepository.findBySiDo(siDoEntity);
        return all.stream().map(SiGunGuEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<EupMyeonDong> getEupMyeonDongList(String sido, String sigungu) {
        SiDoEntity siDoEntity = siDoJpaRepository.findBySiDoName(sido);
        SiGunGuEntity siGunGuEntity = sigunguJpaRepository.findBySiGunGuName(sigungu);
        List<EupMyeonDongEntity> all = eupMyeonDongJpaRepository.findBySiDoAndSiGunGu(siDoEntity, siGunGuEntity);
        return all.stream().map(EupMyeonDongEntity::toDomain).collect(Collectors.toList());
    }
}
