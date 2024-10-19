package com.projeto.ReFood.dto;

public record LoginResponse(String jwt, Long id, String nome, String email) {}
