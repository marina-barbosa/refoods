package com.projeto.ReFood.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com argumentos para todos os campos
@EqualsAndHashCode // Gera os métodos equals e hashCode baseados nos campos da classe
public class OrderItemPK implements Serializable {

  private Long orderId;
  private Long productId;

}
