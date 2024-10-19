package com.projeto.ReFood.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

  @Bean
  public FirebaseApp initializeFirebase() throws IOException {
    String serviceAccountPath = System.getProperty("user.dir") + "/refoods/refood-firebase-key.json";
    FileInputStream serviceAccountStream = new FileInputStream(serviceAccountPath);

    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
        .setStorageBucket("refood-refood.appspot.com")
        .build();
    return FirebaseApp.initializeApp(options);
  }

}
