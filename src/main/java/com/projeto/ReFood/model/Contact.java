package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_contacts")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "contact_id")
  private Long contactId;
  
  @NotBlank(message = "A descrição do contato é obrigatória.")
  @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres.")
  @Column(nullable = false)
  private String description;

  @NotBlank(message = "O telefone é obrigatório.")
  @Pattern(regexp = "\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}", message = "O telefone deve estar no formato correto (ex: (XX) XXXX-XXXX ou (XX) XXXXX-XXXX).")
  @Column(nullable = false, length = 15)
  private String phone;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;

}
