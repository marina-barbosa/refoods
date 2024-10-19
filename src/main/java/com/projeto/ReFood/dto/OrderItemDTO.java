package com.projeto.ReFood.dto;

import com.projeto.ReFood.model.OrderItemPK;

import jakarta.validation.constraints.NotNull;

public record OrderItemDTO(
    @NotNull(message = "O ID do item do pedido não pode ser nulo.") OrderItemPK orderItemId,

    @NotNull(message = "A quantidade não pode ser nula.") int quantity,

    @NotNull(message = "O valor unitário não pode ser nulo.") float unitValue,

    @NotNull(message = "O subtotal não pode ser nulo.") float subtotal,

    @NotNull(message = "O ID do pedido não pode ser nulo.") Long orderId,

    @NotNull(message = "O ID do produto não pode ser nulo.") Long productId) {
}