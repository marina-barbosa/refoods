package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "tb_products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long productId;

  @NotBlank(message = "O nome do produto não pode estar vazio.")
  @Column(name = "name_product", nullable = false)
  private String nameProduct;

  @Column(name = "description_product")
  private String descriptionProduct;

  @Column(name = "url_img_product")
  private String urlImgProduct;

  @NotNull(message = "O valor do produto não pode ser nulo.")
  @Column(name = "value_product", nullable = false)
  private float valueProduct;

  @NotNull(message = "O desconto deve ser um valor válido.")
  @Min(value = 0, message = "O desconto não pode ser menor que 0.")
  @Max(value = 100, message = "O desconto não pode ser maior que 100.")
  @Column(name = "discount", nullable = false)
  private int discount;

  @NotNull(message = "A data de expiração não pode ser nula.")
  @Column(name = "expiration_date", nullable = false)
  private Date expirationDate;

  @NotNull(message = "A quantidade não pode ser nula.")
  @Min(value = 0, message = "A quantidade não pode ser negativa.")
  @Column(name = "quantity", nullable = false)
  private int quantity;

  @NotNull(message = "A categoria do produto não pode ser nula.")
  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private EnumProductCategory categoryProduct;

  @NotNull(message = "A data de adição não pode ser nula.")
  @Column(name = "addition_date", nullable = false)
  private Date additionDate;

  @NotNull(message = "O status ativo deve ser especificado.")
  @Column(name = "active", nullable = false)
  private boolean active;

  @NotNull(message = "O restaurante não pode ser nulo.")
  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;

  @NotEmpty(message = "O pedido deve conter pelo menos um item.")
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private Set<OrderItem> orderItems;

}
