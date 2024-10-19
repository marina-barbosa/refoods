package com.projeto.ReFood.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projeto.ReFood.model.CustomUserDetails;
import com.projeto.ReFood.model.Restaurant;
import com.projeto.ReFood.model.User;
import com.projeto.ReFood.repository.RestaurantRepository;
import com.projeto.ReFood.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final RestaurantRepository restaurantRepository;

  @Autowired
  public CustomUserDetailsService(UserRepository userRepository, RestaurantRepository restaurantRepository) {
    this.userRepository = userRepository;
    this.restaurantRepository = restaurantRepository;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String username) {
    User user = userRepository.findByEmail(username).orElse(null);
    if (user != null) {
      return new CustomUserDetails(user.getUserId(), user.getName(), user.getEmail(), user.getPassword(),
          List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    Restaurant restaurant = restaurantRepository.findByEmail(username).orElse(null);
    if (restaurant != null) {
      return new CustomUserDetails(restaurant.getRestaurantId(), restaurant.getFantasy(), restaurant.getEmail(),
          restaurant.getPassword(),
          List.of(new SimpleGrantedAuthority("ROLE_RESTAURANT")));
    }

    throw new UsernameNotFoundException("Usuário não encontrado.");
  }
}
