package who.is.neighbor.address.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import who.is.neighbor.address.web.response.AddressResponse;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddressServiceTest {

    @Autowired
    @MockBean
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SidoRepository siDoRepository;
    @Autowired
    private SigunguRepository siGunGuRepository;
    @Autowired
    private EupmyeondongRepository eupMyeonDongRepository;

    @Autowired
    @MockBean
    private SidoRepository sidoRepository;
    @Autowired
    @MockBean
    private SigunguRepository sigunguRepository;
    @Autowired
    @MockBean
    private EupmyeondongRepository eupmyeondongRepository;

    private final String sidoName = "서울특별시";
    private final String sigunguName = "강남구";
    private final String eupmyeondongName = "역삼동";
    private final String detailAddress = "강남대로 1234";

    @BeforeEach
    void setUp() {
        addressService = new AddressService(addressRepository, sidoRepository, sigunguRepository, eupmyeondongRepository);
    }

    @Test
    void getSiDoList() {
        SidoEntity siDoEntity = getSidoEntity();

        given(siDoRepository.findAll()).willReturn(List.of(siDoEntity));

        List<Sido> siDoList = addressService.getSiDoList();

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

        List<Sigungu> siGunGuList = addressService.getSiGunGuList(sidoName);

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

        List<Eupmyeondong> eupMyeonDongList = addressService.getEupMyeonDongList(sidoName, sigunguName);

        assertThat(eupMyeonDongList).isNotNull();
        assertThat(eupMyeonDongList.size()).isEqualTo(1);
        assertThat(eupMyeonDongList.get(0).sidoName()).isEqualTo(sidoName);
        assertThat(eupMyeonDongList.get(0).sigunguName()).isEqualTo(sigunguName);
        assertThat(eupMyeonDongList.get(0).eupmyeondongName()).isEqualTo(eupmyeondongName);
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
        given(addressRepository.save(addressEntity)).willReturn(addressEntity);
        AddressResponse addressResponse = addressService.save(request);

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
        AddressUpdateRequest request = new AddressUpdateRequest(detailAddress, AddressType.HOME, true);

        AddressEntity addressEntity = mock(AddressEntity.class);
        when(addressRepository.findById(addressId)).thenReturn(java.util.Optional.of(addressEntity));

        AddressResponse addressResponse = addressService.update(addressId, request);

        assertThat(addressResponse).isNotNull();
    }

    @Test
    void delete() {
        Long addressId = 1L;
        addressService.delete(addressId);
    }
}