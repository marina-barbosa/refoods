package com.projeto.ReFood.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_restaurant_hours")
public class RestaurantHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hours_id")
    private Long id;

    @NotNull(message = "O dia da semana não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private EnumDayOfWeek dayOfWeek;

    @NotNull(message = "O horário de abertura não pode ser nulo.")
    @Column(name = "opening_time", nullable = false)
    private String openingTime;

    @NotNull(message = "O horário de fechamento não pode ser nulo.")
    @Column(name = "closing_time", nullable = false)
    private String closingTime;

    @NotNull(message = "O restaurante não pode ser nulo.")
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
