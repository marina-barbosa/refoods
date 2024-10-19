package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
