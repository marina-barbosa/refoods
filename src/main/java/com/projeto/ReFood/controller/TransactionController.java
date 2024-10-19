package com.projeto.ReFood.controller;

import com.projeto.ReFood.service.TransactionService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.ReFood.dto.TransactionDTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @GetMapping
  public ResponseEntity<List<TransactionDTO>> listAllTransactions() {
    List<TransactionDTO> transactions = transactionService.getAllTransactions();
    return ResponseEntity.ok(transactions);
  }

  @GetMapping("/{transactionId}")
  public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long transactionId) {
    TransactionDTO transactionDTO = transactionService.getTransactionById(transactionId);
    return ResponseEntity.ok(transactionDTO);
  }

  @PostMapping
  public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
    TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{transactionId}")
        .buildAndExpand(createdTransaction.transactionId())
        .toUri();
    return ResponseEntity.created(location).body(createdTransaction);
  }

  @PutMapping("/{transactionId}")
  public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long transactionId,
      @Valid @RequestBody TransactionDTO transactionDTO) {
    TransactionDTO updatedTransaction = transactionService.updateTransaction(transactionId, transactionDTO);
    return ResponseEntity.ok(updatedTransaction);
  }

  @DeleteMapping("/{transactionId}")
  public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
    transactionService.deleteTransaction(transactionId);
    return ResponseEntity.noContent().build();
  }
}
