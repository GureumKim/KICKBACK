package ssafy.authserv.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000",
                        "http://localhost:5173")
                .allowedMethods("GET", "PUT", "POST", "DELETE")
                .allowedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600L);
    }
}
