package com.projeto.ReFood.dto;

import com.projeto.ReFood.model.EnumDayOfWeek;

import jakarta.validation.constraints.NotNull;

public record RestaurantHoursDTO(
    Long id,

    @NotNull(message = "O dia da semana não pode ser nulo.")
    EnumDayOfWeek dayOfWeek,

    @NotNull(message = "O horário de abertura não pode ser nulo.")
    String openingTime,

    @NotNull(message = "O horário de fechamento não pode ser nulo.")
    String closingTime,

    //@NotNull(message = "O ID do restaurante não pode ser nulo.")
    Long restaurantId
) {}
