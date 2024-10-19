package com.projeto.ReFood.dto;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TransactionDTO(

    Long transactionId,

    @NotNull(message = "A data da transação é obrigatória.") 
    Date transactionDate,

    @NotNull(message = "O valor da transação é obrigatório.") 
    @PositiveOrZero(message = "O valor da transação deve ser maior ou igual a zero.") 
    BigDecimal transactionValue, // Usando BigDecimal para precisão

    @NotBlank(message = "O status da transação é obrigatório.") 
    String transactionStatus,

    @NotNull(message = "O ID do cartão é obrigatório.") 
    Long cardId,

    @NotNull(message = "O ID do pedido associado é obrigatório.") 
    Long orderId) {
}
