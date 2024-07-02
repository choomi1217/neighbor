package who.is.neighbor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class NeighborApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeighborApplication.class, args);
    }

}
