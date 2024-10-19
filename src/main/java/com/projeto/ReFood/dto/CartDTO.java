package com.projeto.ReFood.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CartDTO(
    Long cartId,

    @NotNull(message = "O status do carrinho não pode ser nulo.")
    @Size(min = 3, max = 20, message = "O status deve ter entre 3 e 20 caracteres.")
    String status,

    @Min(value = 0, message = "O valor total deve ser maior ou igual a zero.")
    float totalValue,

    @NotNull(message = "O ID do usuário não pode ser nulo.")
    Long userId

) {}
