package com.projeto.ReFood.dto;

import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record ReviewDTO(
    Long reviewId,

    @NotNull(message = "Nota de avaliação não pode ser nula.")
    @Min(value = 1, message = "A nota de avaliação deve ser pelo menos 1.")
    @Max(value = 5, message = "A nota de avaliação deve ser no máximo 5.") int ratingNote,

    @NotNull(message = "Data de avaliação não pode ser nula.")
    @PastOrPresent(message = "A data de avaliação deve ser no passado ou presente.") Date ratingDate,

    @Size(max = 500, message = "O comentário da avaliação deve ter no máximo 500 caracteres.") String ratingComment,

    @NotNull(message = "ID do usuário não pode ser nulo.") Long userId,

    @NotNull(message = "ID do restaurante não pode ser nulo.") Long restaurantId) {
}
