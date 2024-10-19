package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "tb_historical_orders")
public class HistoricalOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "historical_order_id")
  private Long historicalOrderId;

  @NotNull(message = "O status do pedido é obrigatório.")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EnumOrderStatus orderStatus;

  @NotNull(message = "A data de modificação é obrigatória.")
  @PastOrPresent(message = "A data de modificação deve ser no passado ou presente.")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_mod", nullable = false)
  private Date dateModified;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "order_id", nullable = false)
  private Order associatedHistoricalOrder;

}
