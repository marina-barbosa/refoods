package com.projeto.ReFood.dto;

import java.util.Date;

import com.projeto.ReFood.model.EnumProductCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
    Long productId,

    @NotBlank(message = "O nome do produto não pode estar vazio.")
    String nameProd,

    String descriptionProd,

    String urlImgProd,

    @NotNull(message = "O valor do produto não pode ser nulo.")
    float valueProd,

    @NotNull(message = "O desconto deve ser um valor válido.")
    int discount,

    @NotNull(message = "A data de expiração não pode ser nula.")
    Date expirationDate,

    @NotNull(message = "A quantidade não pode ser nulal.")
    int quantity,

    @NotNull(message = "A categoria do produto não pode ser nula.")
    EnumProductCategory categoryProduct,

    @NotNull(message = "A data de adição não pode ser nula.")
    Date additionDate,

    @NotNull(message = "O status ativo deve ser especificado.")
    boolean active,

    @NotNull(message = "O ID do restaurante não pode ser nulo.")
    Long restaurantId
) {}
