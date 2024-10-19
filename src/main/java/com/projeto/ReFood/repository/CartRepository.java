package com.projeto.ReFood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projeto.ReFood.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {}
