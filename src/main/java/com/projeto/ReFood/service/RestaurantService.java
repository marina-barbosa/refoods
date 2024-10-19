package com.projeto.ReFood.service;

import com.projeto.ReFood.repository.RestaurantRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.RestaurantDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.CnpjAlreadyExistsException;
import com.projeto.ReFood.exception.GlobalExceptionHandler.EmailAlreadyExistsException;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.EnumRestaurantCategory;
import com.projeto.ReFood.model.Restaurant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final UtilityService utilityService;
  private final PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public List<RestaurantDTO> getAllRestaurants() {
    return restaurantRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public RestaurantDTO getRestaurantById(Long restaurantId) {
    return restaurantRepository.findById(restaurantId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public RestaurantDTO createRestaurant(@Valid RestaurantDTO restaurantDTO) {

    if (!utilityService.isEmailUnique(restaurantDTO.email())) {
      throw new EmailAlreadyExistsException();
    }

    if (restaurantRepository.existsByCnpj(restaurantDTO.cnpj())) {
      throw new CnpjAlreadyExistsException();
    }

    Restaurant restaurant = convertToEntity(restaurantDTO);
    restaurant.setPassword(passwordEncoder.encode(restaurant.getPassword()));
    restaurant.setDateCreation(LocalDateTime.now());
    restaurant.setLastLogin(null);

    restaurantRepository.save(restaurant);
    return convertToDTO(restaurant);
  }

  @Transactional
  public RestaurantDTO updateRestaurant(Long restaurantId, @Valid RestaurantDTO restaurantDTO) {

    Restaurant restaurant = restaurantRepository.findById(restaurantId)
        .orElseThrow(() -> new NotFoundException());

    if (!utilityService.isEmailUnique(restaurantDTO.email())) {
      throw new EmailAlreadyExistsException();
    }

    if (restaurantRepository.existsByCnpj(restaurantDTO.cnpj())) {
      throw new CnpjAlreadyExistsException();
    }

    restaurant.setCnpj(restaurantDTO.cnpj());
    restaurant.setEmail(restaurantDTO.email());
    restaurant.setFantasy(restaurantDTO.fantasy());
    restaurant.setPassword(passwordEncoder.encode(restaurantDTO.password()));
    restaurant.setUrlBanner(restaurantDTO.urlBanner());
    restaurant.setUrlLogo(restaurantDTO.urlLogo());
    restaurant.setQuantityEvaluations(restaurantDTO.quantityEvaluations());
    restaurant.setTotalEvaluations(restaurantDTO.totalEvaluations());
    restaurant.setAverageRating(restaurantDTO.averageRating());

    Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
    return convertToDTO(updatedRestaurant);
  }

  @Transactional
  public void deleteRestaurant(Long restaurantId) {
    if (!restaurantRepository.existsById(restaurantId)) {
      throw new NotFoundException();
    }
    restaurantRepository.deleteById(restaurantId);
  }

  public RestaurantDTO convertToDTO(Restaurant restaurant) {
    return new RestaurantDTO(
        restaurant.getRestaurantId(),
        restaurant.getCnpj(),
        restaurant.getFantasy(),
        restaurant.getEmail(),
        null, // Não expor a senha
        restaurant.getCategory().name(),
        restaurant.getUrlBanner(),
        restaurant.getUrlLogo(),
        restaurant.getQuantityEvaluations(),
        restaurant.getTotalEvaluations(),
        restaurant.getAverageRating(),
        restaurant.getDateCreation(),
        restaurant.getLastLogin());
  }

  public Restaurant convertToEntity(RestaurantDTO restaurantDTO) {
    Restaurant restaurant = new Restaurant();
    restaurant.setRestaurantId(restaurantDTO.restaurantId());
    restaurant.setCnpj(restaurantDTO.cnpj());
    restaurant.setFantasy(restaurantDTO.fantasy());
    restaurant.setEmail(restaurantDTO.email());
    restaurant.setPassword(restaurantDTO.password());
    restaurant.setCategory(EnumRestaurantCategory.valueOf(restaurantDTO.category()));
    restaurant.setDateCreation(LocalDateTime.now());
    restaurant.setUrlBanner(restaurantDTO.urlBanner());
    restaurant.setUrlLogo(restaurantDTO.urlLogo());
    restaurant.setQuantityEvaluations(restaurantDTO.quantityEvaluations());
    restaurant.setTotalEvaluations(restaurantDTO.totalEvaluations());
    restaurant.setAverageRating(restaurantDTO.averageRating());
    return restaurant;
  }
}
