package com.projeto.ReFood.service;

import com.projeto.ReFood.repository.OrderRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.OrderDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.Order;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private UtilityService utilityService;

  @Transactional(readOnly = true)
  public List<OrderDTO> getAllOrders() {
    return orderRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public OrderDTO getOrderById(Long orderId) {
    return orderRepository.findById(orderId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public OrderDTO createOrder(@Valid OrderDTO orderDTO) {
    Order order = convertToEntity(orderDTO);
    utilityService.associateUser(order::setUser, orderDTO.userId());
    utilityService.associateRestaurant(order::setRestaurant, orderDTO.restaurantId());
    utilityService.associateAddress(order::setAssociatedAddress, orderDTO.addressId());
    order = orderRepository.save(order);
    return convertToDTO(order);
  }

  @Transactional
  public OrderDTO updateOrder(Long orderId, @Valid OrderDTO orderDTO) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException());

    order.setOrderDate(orderDTO.orderDate());
    order.setOrderStatus(orderDTO.orderStatus());
    order.setTotalValue(orderDTO.totalValue());

    utilityService.associateUser(order::setUser, orderDTO.userId());
    utilityService.associateRestaurant(order::setRestaurant, orderDTO.restaurantId());
    utilityService.associateAddress(order::setAssociatedAddress, orderDTO.addressId());

    order = orderRepository.save(order);
    return convertToDTO(order);
  }

  @Transactional
  public void deleteOrder(Long orderId) {
    if (!orderRepository.existsById(orderId)) {
      throw new NotFoundException();
    }
    orderRepository.deleteById(orderId);
  }

  private OrderDTO convertToDTO(Order order) {
    return new OrderDTO(
        order.getOrderId(),
        order.getOrderDate(),
        order.getOrderStatus(),
        order.getTotalValue(),
        order.getUser().getUserId(),
        order.getRestaurant().getRestaurantId(),
        order.getAssociatedAddress().getAddressId());
  }

  private Order convertToEntity(OrderDTO orderDTO) {
    Order order = new Order();
    order.setOrderId(orderDTO.orderId());
    order.setOrderDate(orderDTO.orderDate());
    order.setOrderStatus(orderDTO.orderStatus());
    order.setTotalValue(orderDTO.totalValue());
    utilityService.associateUser(order::setUser, orderDTO.userId());
    utilityService.associateRestaurant(order::setRestaurant, orderDTO.restaurantId());
    utilityService.associateAddress(order::setAssociatedAddress, orderDTO.addressId());
    return order;
  }
}
