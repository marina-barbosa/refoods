package com.projeto.ReFood.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.OrderItemDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.OrderItem;
import com.projeto.ReFood.model.OrderItemPK;
import com.projeto.ReFood.repository.OrderItemRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class OrderItemService {

  @Autowired
  private OrderItemRepository orderItemRepository;
  @Autowired
  private UtilityService utilityService;

  @Transactional(readOnly = true)
  public List<OrderItemDTO> getAllOrderItems() {
    return orderItemRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public OrderItemDTO getOrderItemById(OrderItemPK orderItemId) {
    OrderItem orderItem = orderItemRepository.findById(orderItemId)
        .orElseThrow(() -> new NotFoundException());
    return convertToDTO(orderItem);
  }

  @Transactional
  public OrderItemDTO createOrderItem(@Valid OrderItemDTO orderItemDTO) {
    OrderItem orderItem = convertToEntity(orderItemDTO);
    orderItem = orderItemRepository.save(orderItem);
    return convertToDTO(orderItem);
  }

  @Transactional
  public OrderItemDTO updateOrderItem(OrderItemPK orderItemId, @Valid OrderItemDTO orderItemDTO) {
    OrderItem orderItem = orderItemRepository.findById(orderItemId)
        .orElseThrow(() -> new NotFoundException());

    orderItem.setQuantity(orderItemDTO.quantity());
    orderItem.setUnitValue(orderItemDTO.unitValue());
    orderItem.setSubtotal(orderItemDTO.subtotal());
    utilityService.associateOrder(orderItem::setOrder, orderItemDTO.orderId());
    utilityService.associateProduct(orderItem::setProduct, orderItemDTO.productId());

    orderItem = orderItemRepository.save(orderItem);
    return convertToDTO(orderItem);
  }

  @Transactional
  public void deleteOrderItem(OrderItemPK orderItemId) {
    if (!orderItemRepository.existsById(orderItemId)) {
      throw new NotFoundException();
    }
    orderItemRepository.deleteById(orderItemId);
  }

  public OrderItemDTO convertToDTO(OrderItem orderItem) {
    return new OrderItemDTO(
        orderItem.getOrderItemId(),
        orderItem.getQuantity(),
        orderItem.getUnitValue(),
        orderItem.getSubtotal(),
        orderItem.getOrder().getOrderId(),
        orderItem.getProduct().getProductId());
  }

  public OrderItem convertToEntity(OrderItemDTO orderItemDTO) {
    OrderItem orderItem = new OrderItem();
    OrderItemPK orderItemPK = new OrderItemPK(orderItemDTO.orderId(), orderItemDTO.productId());
    orderItem.setOrderItemId(orderItemPK);
    orderItem.setQuantity(orderItemDTO.quantity());
    orderItem.setUnitValue(orderItemDTO.unitValue());
    orderItem.setSubtotal(orderItemDTO.subtotal());
    utilityService.associateOrder(orderItem::setOrder, orderItemDTO.orderId());
    utilityService.associateProduct(orderItem::setProduct, orderItemDTO.productId());
    return orderItem;
  }
}
