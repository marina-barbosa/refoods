package com.projeto.ReFood.service;

import com.projeto.ReFood.dto.CardDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.Card;
import com.projeto.ReFood.model.Transaction;
import com.projeto.ReFood.repository.CardRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class CardService {

  @Autowired
  private CardRepository cardRepository;
  @Autowired
  private UtilityService utilityService;

  @Transactional(readOnly = true)
  public List<CardDTO> getAllCards() {
    return cardRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CardDTO getCardById(Long cardId) {
    return cardRepository.findById(cardId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public CardDTO createCard(@Valid CardDTO cardDTO) {
    Card card = convertToEntity(cardDTO);
    utilityService.associateUser(card::setUser, cardDTO.userId());
    utilityService.associateTransactions(card, cardDTO.transactionIds());
    card = cardRepository.save(card);
    return convertToDTO(card);
  }

  @Transactional
  public CardDTO updateCard(Long cardId, @Valid CardDTO cardDTO) {
    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new NotFoundException());

    card.setNumber(cardDTO.number());
    card.setHolderName(cardDTO.holderName());
    card.setCpf(cardDTO.cpf());
    card.setValidity(cardDTO.validity());
    card.setCvv(cardDTO.cvv());

    utilityService.associateUser(card::setUser, cardDTO.userId());
    utilityService.associateTransactions(card, cardDTO.transactionIds());

    card = cardRepository.save(card);
    return convertToDTO(card);
  }

  @Transactional
  public void deleteCard(Long cardId) {
    if (!cardRepository.existsById(cardId)) {
      throw new NotFoundException();
    }
    cardRepository.deleteById(cardId);
  }

  private CardDTO convertToDTO(Card card) {
    Set<Long> transactionIds = card.getCardTransactions().stream()
        .map(Transaction::getTransactionId)
        .collect(Collectors.toSet());

    return new CardDTO(
        card.getCardId(),
        card.getNumber(),
        card.getHolderName(),
        card.getCpf(),
        card.getValidity(),
        card.getCvv(),
        card.getUser().getUserId(),
        transactionIds);
  }

  private Card convertToEntity(CardDTO cardDTO) {
    Card card = new Card();
    card.setCardId(cardDTO.cardId());
    card.setNumber(cardDTO.number());
    card.setHolderName(cardDTO.holderName());
    card.setCpf(cardDTO.cpf());
    card.setValidity(cardDTO.validity());
    card.setCvv(cardDTO.cvv());
    utilityService.associateUser(card::setUser, cardDTO.userId());
    utilityService.associateTransactions(card, cardDTO.transactionIds());
    return card;
  }
}