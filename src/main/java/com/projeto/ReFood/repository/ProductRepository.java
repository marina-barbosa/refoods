package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
