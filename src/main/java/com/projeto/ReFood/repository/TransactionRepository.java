package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
