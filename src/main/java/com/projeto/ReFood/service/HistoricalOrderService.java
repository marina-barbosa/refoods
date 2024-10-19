package com.projeto.ReFood.service;

import com.projeto.ReFood.repository.HistoricalOrderRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.HistoricalOrderDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.HistoricalOrder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class HistoricalOrderService {

  @Autowired
  private HistoricalOrderRepository historicalOrderRepository;
  @Autowired
  private UtilityService utilityService;

  @Transactional(readOnly = true)
  public List<HistoricalOrderDTO> getAllHistoricalOrders() {
    return historicalOrderRepository.findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public HistoricalOrderDTO getHistoricalOrderById(Long historyId) {
    return historicalOrderRepository.findById(historyId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public HistoricalOrderDTO createHistoricalOrder(@Valid HistoricalOrderDTO historicalOrderDTO) {
    HistoricalOrder historicalOrder = convertToEntity(historicalOrderDTO);
    historicalOrder = historicalOrderRepository.save(historicalOrder);
    return convertToDTO(historicalOrder);
  }

  @Transactional
  public HistoricalOrderDTO updateHistoricalOrder(Long historyId, @Valid HistoricalOrderDTO historicalOrderDTO) {
    HistoricalOrder historicalOrder = historicalOrderRepository.findById(historyId)
        .orElseThrow(() -> new NotFoundException());

    historicalOrder.setOrderStatus(historicalOrderDTO.orderStatus());
    historicalOrder.setDateModified(historicalOrderDTO.dateModified());
    utilityService.associateUser(historicalOrder::setUser, historicalOrderDTO.userId());
    utilityService.associateRestaurant(historicalOrder::setRestaurant, historicalOrderDTO.restaurantId());
    utilityService.associateOrder(historicalOrder, historicalOrderDTO.orderId());

    historicalOrder = historicalOrderRepository.save(historicalOrder);
    return convertToDTO(historicalOrder);
  }

  @Transactional
  public void deleteHistoricalOrder(Long historyId) {
    if (!historicalOrderRepository.existsById(historyId)) {
      throw new NotFoundException();
    }
    historicalOrderRepository.deleteById(historyId);
  }

  private HistoricalOrderDTO convertToDTO(HistoricalOrder historicalOrder) {
    return new HistoricalOrderDTO(
        historicalOrder.getHistoricalOrderId(),
        historicalOrder.getOrderStatus(),
        historicalOrder.getDateModified(),
        historicalOrder.getAssociatedHistoricalOrder() != null
            ? historicalOrder.getAssociatedHistoricalOrder().getOrderId()
            : null,
        historicalOrder.getRestaurant() != null ? historicalOrder.getRestaurant().getRestaurantId() : null,
        historicalOrder.getUser() != null ? historicalOrder.getUser().getUserId() : null);
  }

  private HistoricalOrder convertToEntity(HistoricalOrderDTO historicalOrderDTO) {
    HistoricalOrder historicalOrder = new HistoricalOrder();
    historicalOrder.setOrderStatus(historicalOrderDTO.orderStatus());
    historicalOrder.setDateModified(historicalOrderDTO.dateModified());
    utilityService.associateUser(historicalOrder::setUser, historicalOrderDTO.userId());
    utilityService.associateRestaurant(historicalOrder::setRestaurant, historicalOrderDTO.restaurantId());
    utilityService.associateOrder(historicalOrder, historicalOrderDTO.orderId());
    return historicalOrder;
  }
}