package com.projeto.ReFood.service;

import com.projeto.ReFood.repository.FavoriteRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.FavoriteDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.Favorite;

@Service
@Validated
public class FavoriteService {

  @Autowired
  private FavoriteRepository favoriteRepository;

  @Autowired
  private UtilityService utilityService;

  @Transactional(readOnly = true)
  public FavoriteDTO getFavoriteById(Long favoriteId) {
    return favoriteRepository.findById(favoriteId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public FavoriteDTO createFavorite(@Valid FavoriteDTO favoriteDTO) {
    Favorite favorite = new Favorite();
    favorite.setAdditionDate(favoriteDTO.additionDate());

    utilityService.associateUser(favorite::setUser, favoriteDTO.userId());
    utilityService.associateRestaurant(favorite::setRestaurant, favoriteDTO.restaurantId());

    favorite = favoriteRepository.save(favorite);
    return convertToDTO(favorite);
  }

  @Transactional
  public FavoriteDTO updateFavorite(Long favoriteId, @Valid FavoriteDTO favoriteDTO) {
    Favorite favorite = favoriteRepository.findById(favoriteId)
        .orElseThrow(() -> new NotFoundException());

    favorite.setAdditionDate(favoriteDTO.additionDate());

    utilityService.associateUser(favorite::setUser, favoriteDTO.userId());
    utilityService.associateRestaurant(favorite::setRestaurant, favoriteDTO.restaurantId());

    favorite = favoriteRepository.save(favorite);
    return convertToDTO(favorite);
  }

  @Transactional
  public void deleteFavorite(Long favoriteId) {
    if (!favoriteRepository.existsById(favoriteId)) {
      throw new NotFoundException();
    }
    favoriteRepository.deleteById(favoriteId);
  }

  private FavoriteDTO convertToDTO(Favorite favorite) {
    return new FavoriteDTO(
        favorite.getFavoriteId(),
        favorite.getAdditionDate(),
        favorite.getUser().getUserId(),
        favorite.getRestaurant().getRestaurantId());
  }
}