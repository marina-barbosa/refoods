package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.Set;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tb_users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @NotBlank(message = "O nome é obrigatório.")
  @Column(nullable = false)
  private String name;
  
  @NotBlank(message = "O e-mail é obrigatório.")
  @Email(message = "O e-mail deve ser um endereço de e-mail válido.")
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "A senha é obrigatória.")
  // @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "A
  // senha deve ter pelo menos 8 caracteres, incluindo pelo menos uma letra
  // maiúscula, uma letra minúscula e um número.")
  @Column(nullable = false)
  private String password;

  @Pattern(regexp = "\\d{10,15}", message = "O telefone deve conter entre 10 e 15 dígitos numéricos.")
  @Column(nullable = true)
  private String phone;

  @NotNull(message = "A data de criação é obrigatória.")
  @Column(name = "date_creation", nullable = false)
  private LocalDateTime dateCreation;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @OneToMany(mappedBy = "user")
  private Set<Address> userAddresses;

  @OneToMany(mappedBy = "user")
  private Set<Favorite> userFavorites;

  @OneToMany(mappedBy = "user")
  private Set<Card> userCards;

  @OneToMany(mappedBy = "user")
  private Set<Notification> userNotifications;

  @OneToMany(mappedBy = "user")
  private Set<Order> userOrders;

  @OneToMany(mappedBy = "user")
  private Set<Cart> userCarts;

  @OneToMany(mappedBy = "user")
  private Set<Review> userReviews;

  @OneToMany(mappedBy = "user")
  private Set<HistoricalOrder> userHistoricalOrders;
}
