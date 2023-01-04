package io.github.mroncatto.itflow.config.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.github.mroncatto.itflow.config.constant.ApplicationConstant.APP_DESC;
import static io.github.mroncatto.itflow.config.constant.ApplicationConstant.APP_NAME;

@Configuration
public class SwaggerBean {

    @Bean
    public OpenAPI customOpenAPI(@Value("${app.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer")))
                .info(new Info().title(APP_NAME).version(appVersion)
                        .description(APP_DESC)
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}
