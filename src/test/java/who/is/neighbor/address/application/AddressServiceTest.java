package who.is.neighbor.address.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    private AddressService sut;

    private final String sidoName = "서울특별시";
    private final String sigunguName = "강남구";
    private final String eupmyeondongName = "역삼동";
    private final String detailAddress = "강남대로 1234";

    @Test
    void getsidolist() {
        SidoEntity siDoEntity = getSidoEntity();

        when(siDoRepository.findAll()).thenReturn(List.of(siDoEntity));

        List<Sido> siDoList = sut.getSidoList();

        assertThat(siDoList).isNotNull();
        assertThat(siDoList.size()).isEqualTo(1);
        assertThat(siDoList.get(0).sidoName()).isEqualTo(sidoName);
    }

    @Test
    void getSigunguList() {
        SidoEntity sidoEntity = getSidoEntity();
        SigunguEntity sigunguEntity = getSigunguEntity(sidoEntity);

        given(siDoRepository.findBySidoName(sidoName)).willReturn(sidoEntity);
        given(sigunguRepository.findBySido(sidoEntity)).willReturn(List.of(sigunguEntity));

        List<Sigungu> siGunGuList = sut.getSiGunGuList(sidoName);

        assertThat(siGunGuList).isNotNull();
        assertThat(siGunGuList.size()).isEqualTo(1);
        assertThat(siGunGuList.get(0).sidoName()).isEqualTo(sidoName);
        assertThat(siGunGuList.get(0).sigunguName()).isEqualTo(sigunguName);
    }

    @Test
    void getEupMyeonDongList() {
        SidoEntity sidoEntity = getSidoEntity();
        SigunguEntity sigunguEntity = getSigunguEntity(sidoEntity);
        EupmyeondongEntity eupmyeondongEntity = getEupmyeondongEntity(sidoEntity, sigunguEntity);

        given(siDoRepository.findBySidoName(sidoName)).willReturn(sidoEntity);
        given(sigunguRepository.findBySidoAndSigunguName(sidoEntity, sigunguName)).willReturn(sigunguEntity);
        given(eupmyeondongRepository.findBySiDoAndSiGunGu(sidoEntity, sigunguEntity)).willReturn(List.of(eupmyeondongEntity));

        List<Eupmyeondong> eupMyeonDongList = sut.getEupMyeonDongList(sidoName, sigunguName);

        assertThat(eupMyeonDongList).isNotNull();
        assertThat(eupMyeonDongList.size()).isEqualTo(1);
        assertThat(eupMyeonDongList.get(0).sidoName()).isEqualTo(sidoName);
        assertThat(eupMyeonDongList.get(0).sigunguName()).isEqualTo(sigunguName);
        assertThat(eupMyeonDongList.get(0).eupmyeondongName()).isEqualTo(eupmyeondongName);
    }

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

        AddressEntity addressEntity = new AddressEntity(sidoEntity, sigunguEntity, eupmyeondongEntity, request);
        given(addressRepository.save(any(AddressEntity.class))).willReturn(addressEntity);
        AddressResponse addressResponse = sut.save(request);

        assertThat(addressResponse).isNotNull();
        assertThat(addressResponse.sido()).isEqualTo(sido);
        assertThat(addressResponse.sigungu()).isEqualTo(sigungu);
        assertThat(addressResponse.eupMyeonDong()).isEqualTo(eupmyeondong);
        assertThat(addressResponse.detailAddress()).isEqualTo(detailAddress);
        assertThat(addressResponse.addressType()).isEqualTo(AddressType.HOME);
        assertThat(addressResponse.addressVerificationStatus()).isFalse();

    }

    @Test
    void update() {
        Long addressId = 1L;
        AddressUpdateRequest request = new AddressUpdateRequest(sidoName, sigunguName, eupmyeondongName, "테스트길 52", AddressType.HOME);

        AddressEntity addressEntity = mock(AddressEntity.class);
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(addressEntity));

        when(sut.update(addressId, request)).thenReturn(new AddressResponse(
                new Sido(sidoName),
                new Sigungu(sidoName, sigunguName),
                new Eupmyeondong(sidoName, sigunguName, eupmyeondongName),
                "테스트길 52",
                AddressType.HOME,
                false
        ));

        AddressResponse addressResponse = sut.update(addressId, request);

        assertThat(addressResponse).isNotNull();
        assertThat(addressResponse.sido().sidoName()).isEqualTo(sidoName);
        assertThat(addressResponse.sigungu().sigunguName()).isEqualTo(sigunguName);
        assertThat(addressResponse.eupMyeonDong().eupmyeondongName()).isEqualTo(eupmyeondongName);
        assertThat(addressResponse.detailAddress()).isEqualTo("테스트길 52");
        assertThat(addressResponse.addressType()).isEqualTo(AddressType.HOME);
    }

    @Test
    void delete() {
        Long addressId = 1L;
        assertThatNoException().isThrownBy(() -> sut.delete(addressId));
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
        return new AddressEntity(sidoEntity, sigunguEntity, eupmyeondongEntity, request);
    }

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

        boolean result = sut.addressVerification(1L, coordinates);
        assertTrue(result);
    }

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

        boolean result = sut.addressVerification(1L, coordinates);
        assertFalse(result);
    }

}