package com.test.project.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI confOpenApi() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("Public Api").description("This is a documentation for test project"));
  }
}
