package config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @name : OpenAPIConfiguration.java
 * @date : 2024-09-06
 * @author : erlia
 * @version : 1.0.0
 */
@Configuration
public class OpenAPIConfiguration {

    private static final String API_NAME = "Axis Spring";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "Axis Spring 레포지토리입니다";

    @Bean
    public OpenAPI OpenAPIConfig() {
        return new OpenAPI()
                .info(new Info().title(API_NAME).description(API_DESCRIPTION).version(API_VERSION));
    }
}