package com.projeto.ReFood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "tb_cards")
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "card_id")
  private Long cardId;

  @NotBlank(message = "O número do cartão é obrigatório.")
  @Pattern(regexp = "\\d{16}", message = "O número do cartão deve ter 16 dígitos.")
  @Column(nullable = false)
  private String number;

  @NotBlank(message = "O nome do titular do cartão é obrigatório.")
  @Size(max = 100, message = "O nome do titular pode ter no máximo 100 caracteres.")
  @Column(nullable = false)
  private String holderName;

  @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos.")
  @Column(nullable = false)
  private String cpf;

  @NotBlank(message = "A validade do cartão é obrigatória.")
  @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "O formato da validade deve ser MM/AA.")
  @Column(nullable = false)
  private String validity;

  @NotBlank(message = "O CVV é obrigatório.")
  @Size(min = 3, max = 3, message = "O CVV deve ter exatamente 3 dígitos.")
  @Column(nullable = false)
  private String cvv;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "card")
  private Set<Transaction> cardTransactions;

}
