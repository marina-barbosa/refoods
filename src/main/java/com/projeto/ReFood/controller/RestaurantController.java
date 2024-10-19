package com.projeto.ReFood.controller;

import com.projeto.ReFood.service.RestaurantService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.ReFood.dto.RestaurantDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.EmailAlreadyExistsException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  @GetMapping
  public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
    List<RestaurantDTO> restaurants = restaurantService.getAllRestaurants();
    return ResponseEntity.ok(restaurants);
  }

  @GetMapping("/{restaurantId}")
  public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long restaurantId) {
    RestaurantDTO restaurant = restaurantService.getRestaurantById(restaurantId);
    return ResponseEntity.ok(restaurant);
  }

  @PostMapping
  public ResponseEntity<RestaurantDTO> createRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
    try {
      RestaurantDTO createdRestaurant = restaurantService.createRestaurant(restaurantDTO);
      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{restaurantId}")
          .buildAndExpand(createdRestaurant.restaurantId())
          .toUri();
      return ResponseEntity.created(location).body(createdRestaurant);
    } catch (EmailAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }
  }

  @PutMapping("/{restaurantId}")
  public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable Long restaurantId,
      @Valid @RequestBody RestaurantDTO restaurantDTO) {
    RestaurantDTO updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurantDTO);
    return ResponseEntity.ok(updatedRestaurant);
  }

  @DeleteMapping("/{restaurantId}")
  public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId) {
    restaurantService.deleteRestaurant(restaurantId);
    return ResponseEntity.noContent().build();
  }
}