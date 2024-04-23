package ssafy.authserv.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;
    @Value("${app.firebase-bucket}")
    private String bucketName;

    @Bean
    public FirebaseApp firebaseInitialization() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ClassPathResource(firebaseConfigPath).getInputStream()
        );
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setStorageBucket(bucketName)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        return FirebaseApp.getInstance();
    }
}
