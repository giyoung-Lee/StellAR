package com.ssafy.stellar.settings;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(
                    List.of(
                            new Server().url("https://k10c105.p.ssafy.io/api").description("Production server"),
                            new Server().url("http://localhost:8080/api").description("Local server")
                    ));
    }

    private Info apiInfo() {
        return new Info()
                .title("StellAR API 문서")
                .description("⭐3D/AR 별자리 앱 StellAR⭐ Swagger API 문서")
                .version("1.0.0");
    }
}
