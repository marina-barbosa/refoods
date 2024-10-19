package com.projeto.ReFood.dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;

public record FavoriteDTO(
    Long favoriteId,

    @NotNull(message = "A data de adição é obrigatória.") Date additionDate,

    @NotNull(message = "O ID do usuário é obrigatório.") Long userId,

    @NotNull(message = "O ID do restaurante é obrigatório.") Long restaurantId) {
}