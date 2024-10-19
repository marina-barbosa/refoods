package com.projeto.ReFood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projeto.ReFood.model.CartItem;
import com.projeto.ReFood.model.CartItemPK;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemPK> {}
