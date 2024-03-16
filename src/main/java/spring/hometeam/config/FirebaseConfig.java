package spring.hometeam.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp createFirebaseApp() throws IOException {
        // resources 폴더 내의 서비스 계정 파일 참조
        FileInputStream serviceAccount = new FileInputStream("./hometeam-220ad-firebase-adminsdk-7lciv-407c1c2a8d.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) { // FirebaseApp이 이미 초기화되었는지 확인
            FirebaseApp.initializeApp(options);
        }

        return FirebaseApp.getInstance();
    }
}
