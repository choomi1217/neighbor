package who.is.neighbor.geometry.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import who.is.neighbor.geometry.application.GeometryService;

@RestController
@RequestMapping("/geometry")
public class GeometryController {

    private final GeometryService geometryService;

    public GeometryController(GeometryService geometryService) {
        this.geometryService = geometryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> createDataFromShap(
            @RequestPart String tableName,
            @RequestPart(value = "file", required = true) MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return geometryService.insertShapeFileToTempFolder(tableName, file);
    }

}
