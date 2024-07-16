package who.is.neighbor.location.application;

import org.springframework.stereotype.Service;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.citizen.infrastructure.entity.CitizenEntity;
import who.is.neighbor.location.infrastructure.LocationJpaRepository;
import who.is.neighbor.location.infrastructure.entity.CitizenLocationEntity;
import who.is.neighbor.location.web.request.LocationRequest;
import who.is.neighbor.location.web.response.LocationResponse;

import java.util.List;

@Service
public class LocationService {

    private final LocationJpaRepository repository;

    public LocationService(LocationJpaRepository repository) {
        this.repository = repository;
    }

    public LocationResponse getMyLocation(long citizenId) {
        CitizenLocationEntity citizenLocationEntity = repository.findById(citizenId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return new LocationResponse(citizenLocationEntity.getLatitude(), citizenLocationEntity.getLongitude());
    }

    public void updateLocation(long citizenId, LocationRequest request) {
        repository.findById(citizenId).ifPresent(citizenLocationEntity -> {
            citizenLocationEntity.updateLocation(request.latitude(), request.longitude());
            repository.save(citizenLocationEntity);
        });
    }

    public List<Citizen> getNearbyUsers(LocationRequest request) {
        List<CitizenEntity> citizenEntities = repository.findNearByCitizen(request.latitude(), request.longitude());
        return citizenEntities.stream().map(Citizen::from).toList();
    }
}
