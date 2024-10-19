package com.projeto.ReFood.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_cart")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_id")
  private Long cartId;

  @NotNull(message = "O status do carrinho não pode ser nulo.")
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private EnumCartStatus status;

  @NotNull(message = "O valor total não pode ser nulo.")
  @Positive(message = "O valor total deve ser positivo.")
  @Column(nullable = false)
  private float totalValue;

  @NotNull(message = "O usuário associado não pode ser nulo.")
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CartItem> CartItems;

}
