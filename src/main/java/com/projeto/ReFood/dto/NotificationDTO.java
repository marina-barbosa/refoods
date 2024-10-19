package com.projeto.ReFood.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record NotificationDTO(
    Long notificationId,

    @NotBlank(message = "A mensagem da notificação não pode estar vazia.") @Size(max = 255, message = "A mensagem da notificação não pode ter mais de 255 caracteres.") String msgNotification,

    boolean msgRead,

    @NotNull(message = "A data de envio não pode ser nula.") @PastOrPresent(message = "A data de envio não pode ser no futuro.") LocalDateTime sendDate,

    @NotNull(message = "O ID do usuário não pode ser nulo.") Long userId,

    @NotNull(message = "O ID do restaurante não pode ser nulo.") Long restaurantId) {
}
