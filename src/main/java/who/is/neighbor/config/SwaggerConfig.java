package who.is.neighbor.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }
    private Info apiInfo() {
        return new Info()
                .title("Neighbor API")
                .description("Neighbor API 명세서입니다.")
                .version("1.0.0");
    }


}
