package com.projeto.ReFood.service;

import com.projeto.ReFood.dto.UserDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.EmailAlreadyExistsException;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.User;
import com.projeto.ReFood.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UtilityService utilityService;
  private final PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public List<UserDTO> getAllUsers() {
    return userRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public UserDTO getUserById(Long userId) {
    return userRepository.findById(userId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public UserDTO createUser(@Valid UserDTO userDTO) {

    if (!utilityService.isEmailUnique(userDTO.email())) {
      throw new EmailAlreadyExistsException();
    }

    User user = convertToEntity(userDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setDateCreation(LocalDateTime.now());
    user.setLastLogin(null);

    return convertToDTO(userRepository.save(user));
  }

  @Transactional
  public UserDTO updateUser(Long userId, @Valid UserDTO userDTO) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException());

    if (!utilityService.isEmailUnique(userDTO.email())) {
      throw new EmailAlreadyExistsException();
    }

    user.setName(userDTO.name());
    user.setEmail(userDTO.email());
    user.setPhone(userDTO.phone());

    return convertToDTO(userRepository.save(user));
  }

  @Transactional
  public void deleteUser(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new NotFoundException();
    }
    userRepository.deleteById(userId);
  }

  private User convertToEntity(UserDTO userDTO) {
    User user = new User();
    user.setUserId(userDTO.userId());
    user.setName(userDTO.name());
    user.setEmail(userDTO.email());
    user.setPhone(userDTO.phone());
    user.setPassword(userDTO.password());
    user.setDateCreation(userDTO.dateCreation());
    user.setLastLogin(userDTO.lastLogin());
    return user;
  }

  private UserDTO convertToDTO(User user) {
    return new UserDTO(
        user.getUserId(),
        user.getName(),
        user.getEmail(),
        user.getPhone(),
        null, // Não expor a senha
        user.getDateCreation(),
        user.getLastLogin());
  }

}