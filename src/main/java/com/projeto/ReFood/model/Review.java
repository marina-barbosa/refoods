package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "tb_reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_id")
  private Long reviewId;

  @NotNull(message = "Nota de avaliação não pode ser nula.")
  @Min(value = 1, message = "A nota de avaliação deve ser pelo menos 1.")
  @Max(value = 5, message = "A nota de avaliação deve ser no máximo 5.")
  @Column(nullable = false)
  private int ratingNote;

  @NotNull(message = "Data de avaliação não pode ser nula.")
  @PastOrPresent(message = "A data de avaliação deve ser no passado ou presente.")
  @Column(nullable = false)
  private Date ratingDate;

  @Size(max = 500, message = "O comentário da avaliação deve ter no máximo 500 caracteres.")
  @Column
  private String ratingComment;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;

}
