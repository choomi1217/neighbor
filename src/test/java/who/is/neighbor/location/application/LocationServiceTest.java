package who.is.neighbor.location.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;
import who.is.neighbor.location.infrastructure.LocationJpaRepository;
import who.is.neighbor.location.infrastructure.entity.CitizenLocationEntity;
import who.is.neighbor.location.web.request.LocationRequest;
import who.is.neighbor.location.web.response.LocationResponse;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @InjectMocks
    private LocationService locationService;

    @Mock
    private LocationJpaRepository locationJpaRepository;

    @DisplayName("사용자의 위치정보를 받아와 DB에 업데이트 합니다.")
    @Test
    void updateCitizenLocation() {
        double expectLatitude = 37.654321;
        double expectLongitude = 127.654321;

        when(locationJpaRepository.findById(1L)).thenReturn(Optional.of(new CitizenLocationEntity(1L, expectLatitude, expectLongitude)));

        assertThatCode(() -> locationService.updateLocation(1L, new LocationRequest(expectLatitude, expectLongitude)))
                .doesNotThrowAnyException();

        LocationResponse actual = locationService.getMyLocation(1L);
        assertThat(actual.latitude()).isEqualTo(expectLatitude);
        assertThat(actual.longitude()).isEqualTo(expectLongitude);
    }

    @DisplayName("사용자 반경 1km 이내의 사용자들을 조회합니다.")
    @Test
    void searchCitizensWithin1kmRadiusOfMe() {
        double latitude = 37.654321;
        double longitude = 127.654321;
        List<CitizenEntity> citizenEntities = getCitizenEntities();

        when(locationJpaRepository.findNearByCitizen(latitude, longitude)).thenReturn(citizenEntities);

        List<Citizen> nearCitizen = locationService.getNearbyUsers(new LocationRequest(latitude, longitude));
        assertThat(nearCitizen).hasSize(2);
    }

    private static List<CitizenEntity> getCitizenEntities() {
        List<CitizenEntity> citizenEntities = List.of(new CitizenEntity(1L, "nickname", null, "01012345678", null, null),
                new CitizenEntity(2L, "nickname2", null, "01012345678", null, null));
        return citizenEntities;
    }

}