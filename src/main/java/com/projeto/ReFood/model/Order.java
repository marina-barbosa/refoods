package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "tb_orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long orderId;

  @NotNull(message = "A data do pedido não pode ser nula.")
  @PastOrPresent(message = "A data do pedido não pode ser no futuro.")
  @Column(nullable = false)
  private Date orderDate;

  @NotNull(message = "O status do pedido não pode ser nulo.")
  @Column(nullable = false)
  private EnumOrderStatus orderStatus;

  @NotNull(message = "O valor total não pode ser nulo.")
  @Positive(message = "O valor total deve ser positivo.")
  @Column(nullable = false)
  private float totalValue;

  @NotNull(message = "O usuário associado não pode ser nulo.")
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @NotNull(message = "O restaurante associado não pode ser nulo.")
  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;

  @NotNull(message = "O endereço associado não pode ser nulo.")
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id", nullable = false)
  private Address associatedAddress;

  @OneToOne(mappedBy = "associatedHistoricalOrder")
  private HistoricalOrder associatedHistoricalOrder;

  @OneToOne(mappedBy = "associatedOrder")
  private Transaction associatedTransaction;
  
  @NotEmpty(message = "O pedido deve conter pelo menos um item.")
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private Set<OrderItem> orderItems;

}
