package who.is.neighbor.address.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import who.is.neighbor.address.domain.Address;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private SidoRepository siDoRepository;
    @Mock
    private SigunguRepository sigunguRepository;
    @Mock
    private EupmyeondongRepository eupmyeondongRepository;

    @InjectMocks
    private AddressService service;

    private final String sidoName = "서울특별시";
    private final String sigunguName = "강남구";
    private final String eupmyeondongName = "역삼동";
    private final String detailAddress = "강남대로 1234";

    @DisplayName("시도 목록을 출력합니다.")
    @Test
    void getsidolist() {
        SidoEntity siDoEntity = getSidoEntity();

        when(siDoRepository.findAll()).thenReturn(List.of(siDoEntity));

        List<Sido> siDoList = service.findSidos();

        assertThat(siDoList).isNotNull();
        assertThat(siDoList.size()).isEqualTo(1);
        assertThat(siDoList.get(0).sidoName()).isEqualTo(sidoName);
    }

    @DisplayName("시군구 목록을 출력합니다.")
    @Test
    void getSigunguList() {
        SidoEntity sidoEntity = getSidoEntity();
        SigunguEntity sigunguEntity = getSigunguEntity(sidoEntity);

        given(siDoRepository.findBySidoName(sidoName)).willReturn(sidoEntity);
        given(sigunguRepository.findBySido(sidoEntity)).willReturn(List.of(sigunguEntity));

        List<Sigungu> siGunGuList = service.findSigungus(sidoName);

        assertThat(siGunGuList).isNotNull();
        assertThat(siGunGuList.size()).isEqualTo(1);
        assertThat(siGunGuList.get(0).sidoName()).isEqualTo(sidoName);
        assertThat(siGunGuList.get(0).sigunguName()).isEqualTo(sigunguName);
    }

    @DisplayName("읍면동 목록을 출력합니다.")
    @Test
    void findEupmyeondongs() {
        SidoEntity sidoEntity = getSidoEntity();
        SigunguEntity sigunguEntity = getSigunguEntity(sidoEntity);
        EupmyeondongEntity eupmyeondongEntity = getEupmyeondongEntity(sidoEntity, sigunguEntity);

        given(siDoRepository.findBySidoName(sidoName)).willReturn(sidoEntity);
        given(sigunguRepository.findBySidoAndSigunguName(sidoEntity, sigunguName)).willReturn(sigunguEntity);
        given(eupmyeondongRepository.findBySiDoAndSiGunGu(sidoEntity, sigunguEntity)).willReturn(List.of(eupmyeondongEntity));

        List<Eupmyeondong> eupMyeonDongList = service.findEupmyeondongs(sidoName, sigunguName);

        assertThat(eupMyeonDongList).isNotNull();
        assertThat(eupMyeonDongList.size()).isEqualTo(1);
        assertThat(eupMyeonDongList.get(0).sidoName()).isEqualTo(sidoName);
        assertThat(eupMyeonDongList.get(0).sigunguName()).isEqualTo(sigunguName);
        assertThat(eupMyeonDongList.get(0).eupmyeondongName()).isEqualTo(eupmyeondongName);
    }

    @DisplayName("주소를 저장합니다.")
    @Test
    void save() {
        Sido sido = new Sido(sidoName);
        Sigungu sigungu = new Sigungu(sidoName, sigunguName);
        Eupmyeondong eupmyeondong = new Eupmyeondong(sidoName, sigunguName, eupmyeondongName);
        AddressRegistrationRequest request = new AddressRegistrationRequest(sidoName, sigunguName, eupmyeondongName, detailAddress, AddressType.HOME);

        SidoEntity sidoEntity = getSidoEntity();
        SigunguEntity sigunguEntity = getSigunguEntity(sidoEntity);
        EupmyeondongEntity eupmyeondongEntity = getEupmyeondongEntity(sidoEntity, sigunguEntity);

        given(siDoRepository.findBySidoName(sidoName)).willReturn(sidoEntity);
        given(sigunguRepository.findBySidoAndSigunguName(sidoEntity, sigunguName)).willReturn(sigunguEntity);
        given(eupmyeondongRepository.findBySiDoAndSiGunGuAndEupMyeonDongName(sidoEntity, sigunguEntity, eupmyeondongName)).willReturn(eupmyeondongEntity);

        AddressEntity addressEntity = new AddressEntity(1L, sidoEntity, sigunguEntity, eupmyeondongEntity, request);
        given(addressRepository.save(any(AddressEntity.class))).willReturn(addressEntity);
        AddressResponse addressResponse = service.save(request);

        assertThat(addressResponse).isNotNull();
        assertThat(addressResponse.sido()).isEqualTo(sido);
        assertThat(addressResponse.sigungu()).isEqualTo(sigungu);
        assertThat(addressResponse.eupMyeonDong()).isEqualTo(eupmyeondong);
        assertThat(addressResponse.detailAddress()).isEqualTo(detailAddress);
        assertThat(addressResponse.addressType()).isEqualTo(AddressType.HOME);
        assertThat(addressResponse.addressVerificationStatus()).isFalse();

    }

    @DisplayName("주소를 수정합니다.")
    @Test
    void update() {
        Long addressId = 1L;
        AddressUpdateRequest request = new AddressUpdateRequest(
                sidoName,
                sigunguName,
                eupmyeondongName,
                "테스트길 52",
                AddressType.HOME
        );

        AddressResponse expect = new AddressResponse(
                new Sido(sidoName),
                new Sigungu(sidoName, sigunguName),
                new Eupmyeondong(sidoName, sigunguName, eupmyeondongName),
                "테스트길 52",
                AddressType.HOME,
                false
        );

        Address address = new Address(
                1L,
                "테스트길 52",
                AddressType.HOME,
                false,
                new Sido(sidoName),
                new Sigungu(sidoName, sigunguName),
                new Eupmyeondong(sidoName, sigunguName, eupmyeondongName)
        );

        AddressEntity addressEntity = mock(AddressEntity.class);
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(addressEntity));
        when(addressEntity.toDomain()).thenReturn(address);

        AddressResponse actual = service.update(addressId, request);

        assertThat(actual).isNotNull();
        assertThat(actual.sido().sidoName()).isEqualTo(expect.sido().sidoName());
        assertThat(actual.sigungu().sigunguName()).isEqualTo(expect.sigungu().sigunguName());
        assertThat(actual.eupMyeonDong().eupmyeondongName()).isEqualTo(expect.eupMyeonDong().eupmyeondongName());
        assertThat(actual.detailAddress()).isEqualTo(expect.detailAddress());
        assertThat(actual.addressType()).isEqualTo(expect.addressType());
    }

    @DisplayName("주소를 삭제합니다.")
    @Test
    void delete() {
        Long addressId = 1L;
        assertThatNoException().isThrownBy(() -> service.delete(addressId));
    }

    private SidoEntity getSidoEntity() {
        Sido sido = new Sido(sidoName);
        return new SidoEntity(sido);
    }

    private SigunguEntity getSigunguEntity(SidoEntity sidoEntity) {
        Sigungu sigungu = new Sigungu(sidoName, sigunguName);
        return new SigunguEntity(sidoEntity, sigungu);
    }

    private EupmyeondongEntity getEupmyeondongEntity(SidoEntity sidoEntity, SigunguEntity sigunguEntity) {
        Eupmyeondong eupmyeondong = new Eupmyeondong(sidoName, sigunguName, eupmyeondongName);
        return new EupmyeondongEntity(sidoEntity, sigunguEntity, eupmyeondong);
    }

    private AddressEntity getAddressEntity() {
        AddressRegistrationRequest request = new AddressRegistrationRequest(sidoName, sigunguName, eupmyeondongName, detailAddress, AddressType.HOME);

        SidoEntity sidoEntity = getSidoEntity();
        SigunguEntity sigunguEntity = getSigunguEntity(sidoEntity);
        EupmyeondongEntity eupmyeondongEntity = getEupmyeondongEntity(sidoEntity, sigunguEntity);
        return new AddressEntity(1L, sidoEntity, sigunguEntity, eupmyeondongEntity, request);
    }

    @DisplayName("특정 구역 안에 주소가 포함 되어 있는지 확인합니다.")
    @Test
    void testAddressVerification_InsidePolygon() {
        AddressEntity addressEntity = mock(AddressEntity.class);
        EupmyeondongEntity eupmyeondongEntity = mock(EupmyeondongEntity.class);
        Polygon polygon = mock(Polygon.class);
        GeometryFactory geometryFactory = new GeometryFactory();

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(addressEntity));
        when(addressEntity.getEupmyeondong()).thenReturn(eupmyeondongEntity);
        when(eupmyeondongEntity.getPolygon()).thenReturn(polygon);

        Coordinates coordinates = new Coordinates(37.5665, 126.9784);
        Point point = geometryFactory.createPoint(new Coordinate(coordinates.longitude(), coordinates.latitude()));
        when(polygon.contains(point)).thenReturn(true);

        boolean result = service.addressVerification(1L, coordinates);
        assertTrue(result);
    }

    @DisplayName("특정 구역 밖에 주소가 포함 되어 있는지 확인합니다.")
    @Test
    void testAddressVerification_OutsidePolygon() {
        AddressEntity addressEntity = mock(AddressEntity.class);
        EupmyeondongEntity eupmyeondongEntity = mock(EupmyeondongEntity.class);
        Polygon polygon = mock(Polygon.class);
        GeometryFactory geometryFactory = new GeometryFactory();

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(addressEntity));
        when(addressEntity.getEupmyeondong()).thenReturn(eupmyeondongEntity);
        when(eupmyeondongEntity.getPolygon()).thenReturn(polygon);

        Coordinates coordinates = new Coordinates(37.5665, 126.9784);
        Point point = geometryFactory.createPoint(new Coordinate(coordinates.longitude(), coordinates.latitude()));
        when(polygon.contains(point)).thenReturn(false);

        boolean result = service.addressVerification(1L, coordinates);
        assertFalse(result);
    }

}