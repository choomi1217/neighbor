package who.is.neighbor.address.application;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.postgresql.geometric.PGpolygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import who.is.neighbor.address.domain.Eupmyeondong;
import who.is.neighbor.address.domain.Sido;
import who.is.neighbor.address.domain.Sigungu;
import who.is.neighbor.address.infrastructure.AddressRepository;
import who.is.neighbor.address.infrastructure.EupmyeondongRepository;
import who.is.neighbor.address.infrastructure.SidoRepository;
import who.is.neighbor.address.infrastructure.SigunguRepository;
import who.is.neighbor.address.infrastructure.entity.AddressEntity;
import who.is.neighbor.address.infrastructure.entity.EupmyeondongEntity;
import who.is.neighbor.address.infrastructure.entity.SidoEntity;
import who.is.neighbor.address.infrastructure.entity.SigunguEntity;
import who.is.neighbor.address.web.request.AddressRegistrationRequest;
import who.is.neighbor.address.web.request.AddressUpdateRequest;
import who.is.neighbor.address.web.request.Coordinates;
import who.is.neighbor.address.web.response.AddressResponse;
import who.is.neighbor.address.web.response.AddressVerifiedResponse;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final SidoRepository siDoRepository;
    private final SigunguRepository sigunguRepository;
    private final EupmyeondongRepository eupMyeonDongRepository;

    public AddressService(AddressRepository addressRepository, SidoRepository siDoRepository, SigunguRepository sigunguRepository, EupmyeondongRepository eupMyeonDongRepository) {
        this.addressRepository = addressRepository;
        this.siDoRepository = siDoRepository;
        this.sigunguRepository = sigunguRepository;
        this.eupMyeonDongRepository = eupMyeonDongRepository;
    }

    public AddressResponse save(AddressRegistrationRequest request) {
        SidoEntity sido = siDoRepository.findBySidoName(request.sidoName());
        SigunguEntity sigungu = sigunguRepository.findBySidoAndSigunguName(sido, request.sigunguName());
        EupmyeondongEntity eupMyeonDong = eupMyeonDongRepository.findBySiDoAndSiGunGuAndEupMyeonDongName(sido, sigungu, request.eupmyeondongName());
        AddressEntity save = addressRepository.save(new AddressEntity(sido, sigungu, eupMyeonDong, request));
        return save.toDomain();
    }

    public List<Sido> getSiDoList() {
        List<SidoEntity> all = siDoRepository.findAll();
        return all.stream().map(SidoEntity::toDomain).toList();
    }

    public List<Sigungu> getSiGunGuList(String sidoName) {
        SidoEntity sido = siDoRepository.findBySidoName(sidoName);
        List<SigunguEntity> all = sigunguRepository.findBySido(sido);
        return all.stream().map(SigunguEntity::toDomain).toList();
    }

    public List<Eupmyeondong> getEupMyeonDongList(String sidoName, String sigunguName) {
        SidoEntity siDo = siDoRepository.findBySidoName(sidoName);
        SigunguEntity sigungu = sigunguRepository.findBySidoAndSigunguName(siDo, sigunguName);
        List<EupmyeondongEntity> all = eupMyeonDongRepository.findBySiDoAndSiGunGu(siDo, sigungu);
        return all.stream().map(EupmyeondongEntity::toDomain).toList();
    }

    @Transactional
    public AddressResponse update(Long addressId, AddressUpdateRequest request) {
        AddressEntity addressEntity = addressRepository.findById(addressId).orElseThrow();
        addressEntity.update(request);
        return addressEntity.toDomain();
    }

    public void delete(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    public boolean addressVerification(Long addressId, Coordinates coordinates) {

        AddressEntity addressEntity = addressRepository.findById(addressId).orElseThrow(() -> new IllegalArgumentException("주소를 먼저 등록해주세요."));

        Polygon polygon = addressEntity.getEupmyeondong().getPolygon();

        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(coordinates.longitude(), coordinates.latitude()));

        return polygon.contains(point);
    }
}
