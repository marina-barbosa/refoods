package com.projeto.ReFood.controller;

import com.projeto.ReFood.dto.UserDTO;
import com.projeto.ReFood.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<UserDTO>> listAllUsers() {
    List<UserDTO> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
    UserDTO userDTO = userService.getUserById(userId);
    return ResponseEntity.ok(userDTO);
  }

  @PostMapping
  public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
    UserDTO createdUser = userService.createUser(userDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{userId}")
        .buildAndExpand(createdUser.userId())
        .toUri();
    return ResponseEntity.created(location).body(createdUser);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO) {
    UserDTO updatedUser = userService.updateUser(userId, userDTO);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
