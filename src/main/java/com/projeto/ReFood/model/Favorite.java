package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "tb_favorites")
public class Favorite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "favorite_id")
  private Long favoriteId;
  
  @NotNull(message = "A data de adição é obrigatória.")
  @Temporal(TemporalType.TIMESTAMP) // Para armazenar data e hora
  @Column(nullable = false)
  private Date additionDate;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;
}
