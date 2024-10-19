package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
  Optional<Restaurant> findByEmail(String email);
  boolean existsByEmail(String email);
  boolean existsByCnpj(String cnpj);
  Optional<Restaurant> findByCnpj(String cnpj);
}
