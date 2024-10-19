package com.projeto.ReFood.controller;

import com.projeto.ReFood.service.HistoricalOrderService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.ReFood.dto.HistoricalOrderDTO;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/historical-orders")
public class HistoricalOrderController {

  @Autowired
  private HistoricalOrderService historicalOrderService;

  @GetMapping
  public ResponseEntity<List<HistoricalOrderDTO>> listAllHistoricalOrders() {
    List<HistoricalOrderDTO> historicalOrders = historicalOrderService.getAllHistoricalOrders();
    return ResponseEntity.ok(historicalOrders);
  }

  @GetMapping("/{historyId}")
  public ResponseEntity<HistoricalOrderDTO> getHistoricalOrderById(@PathVariable Long historyId) {
    HistoricalOrderDTO historicalOrderDTO = historicalOrderService.getHistoricalOrderById(historyId);
    return ResponseEntity.ok(historicalOrderDTO);
  }

  @PostMapping
  public ResponseEntity<HistoricalOrderDTO> createHistoricalOrder(
      @Valid @RequestBody HistoricalOrderDTO historicalOrderDTO) {
    HistoricalOrderDTO createdOrder = historicalOrderService.createHistoricalOrder(historicalOrderDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{historyId}")
        .buildAndExpand(createdOrder.historicalOrderId())
        .toUri();
    return ResponseEntity.created(location).body(createdOrder);
  }

  @PutMapping("/{historyId}")
  public ResponseEntity<HistoricalOrderDTO> updateHistoricalOrder(@PathVariable Long historyId,
      @Valid @RequestBody HistoricalOrderDTO historicalOrderDTO) {
    HistoricalOrderDTO updatedOrder = historicalOrderService.updateHistoricalOrder(historyId, historicalOrderDTO);
    return ResponseEntity.ok(updatedOrder);
  }

  @DeleteMapping("/{historyId}")
  public ResponseEntity<Void> deleteHistoricalOrder(@PathVariable Long historyId) {
    historicalOrderService.deleteHistoricalOrder(historyId);
    return ResponseEntity.noContent().build();
  }
}
