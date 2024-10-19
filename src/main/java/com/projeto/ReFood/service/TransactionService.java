package com.projeto.ReFood.service;

import com.projeto.ReFood.repository.TransactionRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.TransactionDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.EnumTransactionStatus;
import com.projeto.ReFood.model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UtilityService utilityService;

    @Transactional(readOnly = true)
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository
            .findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TransactionDTO getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
            .map(this::convertToDTO)
            .orElseThrow(() -> new NotFoundException());
    }

    @Transactional
    public TransactionDTO createTransaction(@Valid TransactionDTO transactionDTO) {
        Transaction transaction = convertToEntity(transactionDTO);
        utilityService.associateCard(transaction::setCard, transactionDTO.cardId());
        utilityService.associateOrder(transaction::setAssociatedOrder, transactionDTO.orderId());
        
        transaction = transactionRepository.save(transaction);
        return convertToDTO(transaction);
    }

    @Transactional
    public TransactionDTO updateTransaction(Long transactionId, @Valid TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new NotFoundException());

        transaction.setTransactionDate(transactionDTO.transactionDate());
        transaction.setTransactionValue(transactionDTO.transactionValue());
        transaction.setTransactionStatus(EnumTransactionStatus.valueOf(transactionDTO.transactionStatus()));
        utilityService.associateCard(transaction::setCard, transactionDTO.cardId());
        utilityService.associateOrder(transaction::setAssociatedOrder, transactionDTO.orderId());

        transaction = transactionRepository.save(transaction);
        return convertToDTO(transaction);
    }

    @Transactional
    public void deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new NotFoundException();
        }
        transactionRepository.deleteById(transactionId);
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
            transaction.getTransactionId(),
            transaction.getTransactionDate(),
            transaction.getTransactionValue(),
            transaction.getTransactionStatus().name(),
            transaction.getCard().getCardId(), 
            transaction.getAssociatedOrder().getOrderId() 
        );
    }

    private Transaction convertToEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionDTO.transactionId());
        transaction.setTransactionDate(transactionDTO.transactionDate());
        transaction.setTransactionValue(transactionDTO.transactionValue());
        transaction.setTransactionStatus(EnumTransactionStatus.valueOf(transactionDTO.transactionStatus())); 
        return transaction;
    }
}