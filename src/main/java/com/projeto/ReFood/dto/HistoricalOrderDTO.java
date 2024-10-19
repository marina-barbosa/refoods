package com.projeto.ReFood.dto;

import com.projeto.ReFood.model.EnumOrderStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;

public record HistoricalOrderDTO(

    Long historicalOrderId,

    @NotNull(message = "O status do pedido é obrigatório.") EnumOrderStatus orderStatus,

    @NotNull(message = "A data de modificação é obrigatória.") @PastOrPresent(message = "A data de modificação deve ser no passado ou presente.") Date dateModified,

    @NotNull(message = "O ID do usuário é obrigatório.") Long userId,

    @NotNull(message = "O ID do restaurante é obrigatório.") Long restaurantId,

    @NotNull(message = "O ID do pedido é obrigatório.") Long orderId) {
}
