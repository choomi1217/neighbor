package who.is.neighbor.location.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import who.is.neighbor.citizen.domain.Citizen;
import who.is.neighbor.location.application.LocationService;
import who.is.neighbor.location.web.request.LocationRequest;

import java.util.List;

@RestController
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @PostMapping("/update-location")
    public ResponseEntity<HttpStatus> updateLocation(@RequestParam long citizenId, @RequestBody LocationRequest request) {
        service.updateLocation(citizenId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Citizen>> getNearbyUsers(@RequestBody LocationRequest request) {
        return ResponseEntity.ok(service.getNearbyUsers(request));
    }
}
