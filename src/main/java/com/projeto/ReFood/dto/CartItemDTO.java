package com.projeto.ReFood.dto;

import com.projeto.ReFood.model.CartItemPK;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemDTO(
    @NotNull(message = "O ID do item do carrinho não pode ser nulo.") CartItemPK cartItemId,

    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.") int quantity,

    @NotNull(message = "O valor unitário não pode ser nulo.") float unitValue,

    @Min(value = 0, message = "O preço deve ser maior ou igual a zero.") float subtotal,

    @NotNull(message = "O ID do pedido não pode ser nulo.") Long cartId,

    @NotNull(message = "O ID do produto não pode ser nulo.") Long productId) {}
