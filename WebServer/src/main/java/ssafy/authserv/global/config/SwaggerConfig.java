package ssafy.authserv.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "KickBack auth-Server API 명세서",
                description = "Spring boot Framework",
                version = "v1"
        )
)
public class SwaggerConfig {
}
