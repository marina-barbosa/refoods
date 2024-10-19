package com.projeto.ReFood.dto;

import com.projeto.ReFood.model.EnumOrderStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record OrderDTO(
    Long orderId,

    @NotNull(message = "A data do pedido não pode ser nula.")
    @PastOrPresent(message = "A data do pedido não pode ser no futuro.")
    Date orderDate,

    @NotNull(message = "O status do pedido não pode ser nulo.")
    EnumOrderStatus orderStatus,

    @NotNull(message = "O valor total não pode ser nulo.")
    @Positive(message = "O valor total deve ser positivo.")
    float totalValue,

    @NotNull(message = "O ID do usuário não pode ser nulo.")
    @Positive(message = "O ID do usuário deve ser positivo.")
    Long userId,

    @NotNull(message = "O ID do restaurante não pode ser nulo.")
    @Positive(message = "O ID do restaurante deve ser positivo.")
    Long restaurantId,

    @NotNull(message = "O ID do endereço não pode ser nulo.")
    @Positive(message = "O ID do endereço deve ser positivo.")
    Long addressId
) {}
