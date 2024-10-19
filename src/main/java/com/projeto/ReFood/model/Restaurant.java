package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "tb_restaurants")
public class Restaurant {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @NotBlank(message = "CNPJ não pode estar vazio.")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter exatamente 14 dígitos.")
    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @NotBlank(message = "Nome fantasia não pode estar vazio.")
    @Size(min = 3, max = 100, message = "Nome fantasia deve ter entre 3 e 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String fantasy;

    @NotBlank(message = "Email não pode estar vazio.")
    @Email(message = "Email deve ser válido.")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres.")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Senha não pode estar vazia.")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres.")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column
    private LocalDateTime lastLogin;

    @Enumerated(EnumType.STRING)
    private EnumRestaurantCategory category;

    @Pattern(regexp = "^(http|https)://.*$", message = "A URL do banner deve ser válida.")
    @Column
    private String urlBanner;

    @Pattern(regexp = "^(http|https)://.*$", message = "A URL do logo deve ser válida.")
    @Column
    private String urlLogo;

    @Min(value = 0, message = "A quantidade de avaliações deve ser no mínimo 0.")
    @Column(nullable = false)
    private int quantityEvaluations = 0;

    @Min(value = 0, message = "O total de avaliações deve ser no mínimo 0.")
    @Column(nullable = false)
    private int totalEvaluations = 0;

    @Min(value = 0, message = "A avaliação média deve ser no mínimo 0.")
    @Max(value = 5, message = "A avaliação média deve ser no máximo 5.")
    @Column(nullable = false)
    private float averageRating = 0.0f;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> restaurantContacts;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> restaurantAddresses;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> restaurantProducts;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> restaurantReviews;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorite> restaurantFavorites;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> restaurantOrders;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HistoricalOrder> restaurantHistoricalOrders;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> restaurantNotifications;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RestaurantHours> restaurantHours;

}
