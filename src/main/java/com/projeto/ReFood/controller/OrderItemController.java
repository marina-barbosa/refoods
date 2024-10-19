package com.projeto.ReFood.controller;

import com.projeto.ReFood.dto.OrderItemDTO;
import com.projeto.ReFood.model.OrderItemPK;
import com.projeto.ReFood.service.OrderItemService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

  @Autowired
  private OrderItemService orderItemService;

  @GetMapping
  public ResponseEntity<List<OrderItemDTO>> listAllOrderItems() {
    List<OrderItemDTO> orderItems = orderItemService.getAllOrderItems();
    return ResponseEntity.ok(orderItems);
  }

  @GetMapping("/{orderItemId}")
  public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable OrderItemPK orderItemId) {
    OrderItemDTO orderItemDTO = orderItemService.getOrderItemById(orderItemId);
    return ResponseEntity.ok(orderItemDTO);
  }

  @PostMapping
  public ResponseEntity<OrderItemDTO> createOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) {
    OrderItemDTO createdOrderItem = orderItemService.createOrderItem(orderItemDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{orderItemId}")
        .buildAndExpand(createdOrderItem.orderItemId())
        .toUri();
    return ResponseEntity.created(location).body(createdOrderItem);
  }

  @PutMapping("/{orderItemId}")
  public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable OrderItemPK orderItemId,
      @Valid @RequestBody OrderItemDTO orderItemDTO) {
    OrderItemDTO updatedOrderItem = orderItemService.updateOrderItem(orderItemId, orderItemDTO);
    return ResponseEntity.ok(updatedOrderItem);
  }

  @DeleteMapping("/{orderItemId}")
  public ResponseEntity<Void> deleteOrderItem(@PathVariable OrderItemPK orderItemId) {
    orderItemService.deleteOrderItem(orderItemId);
    return ResponseEntity.noContent().build();
  }
}
