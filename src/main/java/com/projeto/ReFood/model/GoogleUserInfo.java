package com.projeto.ReFood.model;

public record GoogleUserInfo(
    String sub,
    String name,
    String given_name,
    String family_name,
    String email,
    String picture
) {}

