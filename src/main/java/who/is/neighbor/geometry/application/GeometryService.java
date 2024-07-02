package who.is.neighbor.geometry.application;

import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import who.is.neighbor.address.infrastructure.EupmyeondongRepository;
import who.is.neighbor.address.infrastructure.GeometryRepository;
import who.is.neighbor.address.infrastructure.SidoRepository;
import who.is.neighbor.address.infrastructure.SigunguRepository;
import who.is.neighbor.enums.GeometryTableFactory;
import who.is.neighbor.geometry.application.manager.GeometryEntityManager;
import who.is.neighbor.geometry.application.manager.ManagerFactory;
import who.is.neighbor.utility.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

@Service
public class GeometryService {

    private final SidoRepository sidoRepository;
    private final SigunguRepository sigunguRepository;
    private final EupmyeondongRepository eupmyeondongRepository;
    private final ShapeFileService shapeFileService;

    @Value("${java.io.temp-directory}")
    private String tempDirectory;

    public GeometryService(SidoRepository sidoRepository, SigunguRepository sigunguRepository, EupmyeondongRepository eupmyeondongRepository, ShapeFileService shapeFileService) {
        this.sidoRepository = sidoRepository;
        this.sigunguRepository = sigunguRepository;
        this.eupmyeondongRepository = eupmyeondongRepository;
        this.shapeFileService = shapeFileService;
    }

    public ResponseEntity<String> insertShapeFileToTempFolder(String tableName, MultipartFile file) {
        String tempPath = tempDirectory + "/" + tableName + "/" + file.getOriginalFilename();
        try {
            FileUtils fileUtils = new FileUtils();
            fileUtils.unzip(getSaveFileToTempDirectory(tempPath, file).zipFile);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file");
        }

        return insertResult(tableName, tempPath);
    }

    private ResponseEntity<String> insertResult(String tableName, String tempPath) {
        try {
            FeatureIterator<SimpleFeature> shapeReaderIterator = shapeFileService.shapeReader(tempPath);

            ManagerFactory managerFactory = new ManagerFactory(sidoRepository, sigunguRepository, eupmyeondongRepository);
            GeometryEntityManager manager = managerFactory.getManager(tableName);
            manager.insertTable(shapeReaderIterator);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("File uploaded and extracted successfully");
    }

    private SaveZip getSaveFileToTempDirectory(String tempPath, MultipartFile file) {

        File zipFile = new File(tempPath);
        try (FileOutputStream fos = new FileOutputStream(zipFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new SaveZip(tempPath, zipFile);
    }

    private record SaveZip(String tempPath, File zipFile) {
    }
}
