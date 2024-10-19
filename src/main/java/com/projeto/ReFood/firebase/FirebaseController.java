package com.projeto.ReFood.firebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {

  @Autowired
  private FirebaseService firebaseService;

  @PostMapping("/upload")
  public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile,
      @RequestParam("imageName") String imageName) {
    try {
      firebaseService.upload(imageFile, imageName);
      return new ResponseEntity<>("Upload successful!", HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>("Failed to upload image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/image/{imageName}")
  public ResponseEntity<String> getImageUrl(
      @PathVariable String imageName,
      @RequestParam(required = false) String firebaseToken) {

    if (firebaseToken == null || firebaseToken.isEmpty()) {
      return ResponseEntity.badRequest()
          .body("Token Firebase é obrigatório.");
    }

    try {
      String imageUrl = firebaseService.getImageUrl(imageName, firebaseToken);
      return ResponseEntity.ok(imageUrl);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Erro ao gerar a URL da imagem: " + e.getMessage());
    }
  }
}
