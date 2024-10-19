package com.projeto.ReFood.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressDTO(
    Long addressId,

    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O formato do CEP deve ser 12345-678.")
    String cep,

    @NotBlank(message = "O estado é obrigatório.")
    @Size(min = 2, max = 2, message = "O estado deve ter exatamente 2 letras.")
    String state,

    @NotBlank(message = "O bairro é obrigatório.")
    String district,

    @NotBlank(message = "A rua é obrigatória.")
    String street,

    @NotBlank(message = "O número é obrigatório.")
    String number,

    String complement, // opcional

    @NotBlank(message = "O tipo de endereço é obrigatório.")
    String addressType,

    boolean isStandard, // default = false

    Long userId,
    Long restaurantId,
    Long associatedOrderId
) { }