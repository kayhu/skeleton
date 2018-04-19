package org.iakuh.skeleton.api.config;

import static springfox.documentation.builders.PathSelectors.any;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(new ApiInfoBuilder()
            .title("Api Documentation")
            .description("This API uses HTTP Basic Authentication.\n\n"
                + "http://localhost:8080/api/documentation/swagger-ui.html")
            .build())
        .select()
        .apis(RequestHandlerSelectors.basePackage("org.iakuh.skeleton.api.controller"))
        .paths(any())
        .build()
        .securitySchemes(Lists.newArrayList(new BasicAuth("Auth")));
  }
}
