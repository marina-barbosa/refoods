package com.projeto.ReFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projeto.ReFood.dto.LoginRequest;
import com.projeto.ReFood.dto.LoginResponse;
import com.projeto.ReFood.model.TokenRequest;
import com.projeto.ReFood.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> createAuthenticationToken(@RequestBody LoginRequest loginRequest) {
    return authService.authenticateUser(loginRequest.email(), loginRequest.password());
  }

  @PostMapping("/login/success")
  public ResponseEntity<?> loginSuccess(@RequestBody TokenRequest tokenRequest) {
    return authService.handleGoogleLoginSuccess(tokenRequest.idToken());
  }
}
