package com.projeto.ReFood.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "O email é obrigatório.") String email,

    @NotBlank(message = "A senha é obrigatória.") String password) {

}
