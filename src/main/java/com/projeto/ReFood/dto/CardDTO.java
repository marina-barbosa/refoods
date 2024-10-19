package com.projeto.ReFood.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CardDTO(
    Long cardId,

    @NotBlank(message = "O número do cartão é obrigatório.") @Pattern(regexp = "\\d{16}", message = "O número do cartão deve ter 16 dígitos.") String number,

    @NotBlank(message = "O nome do titular do cartão é obrigatório.") @Size(max = 100, message = "O nome do titular pode ter no máximo 100 caracteres.") String holderName,

    @NotBlank(message = "O CPF é obrigatório.") 
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos.") 
    String cpf,

    @NotBlank(message = "A validade do cartão é obrigatória.") @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "O formato da validade deve ser MM/AA.") String validity,

    @NotBlank(message = "O CVV é obrigatório.") @Size(min = 3, max = 3, message = "O CVV deve ter exatamente 3 dígitos.") String cvv,

    Long userId,
    Set<Long> transactionIds) {
}
