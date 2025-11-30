package com.parkwhizz.Parwhizz.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path:}")
    private String firebaseConfigPath;
    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        try {
            Resource resource = resourceLoader.getResource("classpath:parkbuzzPushNotification.json");
            
            // Check if resource exists
            if (!resource.exists()) {
                System.out.println("Firebase config not found - push notifications disabled");
                return null;
            }
            
            InputStream serviceAccount = resource.getInputStream();

//        FileInputStream serviceAccount =
//                new FileInputStream("src/main/resources/parkbuzzPushNotification.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            System.out.println("Firebase initialization failed - push notifications disabled: " + e.getMessage());
            return null;
        }
    }
}
