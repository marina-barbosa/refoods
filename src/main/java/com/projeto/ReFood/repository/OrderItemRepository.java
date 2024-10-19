package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.OrderItem;
import com.projeto.ReFood.model.OrderItemPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
