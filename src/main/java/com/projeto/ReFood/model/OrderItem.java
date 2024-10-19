package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_order_items")
public class OrderItem {

  @EmbeddedId
  private OrderItemPK orderItemId;

  @NotNull(message = "A quantidade não pode ser nula.")
  @Column(nullable = false)
  private int quantity;

  @NotNull(message = "O valor unitário não pode ser nulo.")
  @Column(nullable = false)
  private float unitValue;

  @NotNull(message = "O subtotal não pode ser nulo.")
  @Column(nullable = false)
  private float subtotal;

  @NotNull(message = "O pedido não pode ser nulo.")
  @ManyToOne
  @MapsId("orderId")
  @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
  private Order order;

  @NotNull(message = "O produto não pode ser nulo.")
  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
  private Product product;

}
