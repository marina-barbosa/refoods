package com.projeto.ReFood.firebase;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseService {

  @Value("${image.base.url}")
  private String imageBaseUrl;

  public void upload(MultipartFile imageFile, String imageName) throws IOException {
    InputStream inputStream = imageFile.getInputStream();
    Bucket bucket = StorageClient.getInstance().bucket();
    bucket.create(imageName, inputStream, "image/jpeg");
  }

  public String getImageUrl(String imageName, String firebaseToken) {
    return imageBaseUrl + imageName + "?alt=media&token=" + firebaseToken;
  }

}




