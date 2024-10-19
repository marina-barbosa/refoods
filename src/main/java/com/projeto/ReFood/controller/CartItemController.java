package com.projeto.ReFood.controller;

import com.projeto.ReFood.dto.CartItemDTO;
import com.projeto.ReFood.model.CartItemPK;
import com.projeto.ReFood.service.CartItemService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

  @Autowired
  private CartItemService cartItemService;

  @GetMapping
  public ResponseEntity<List<CartItemDTO>> listAllCartItems() {
    List<CartItemDTO> cartItems = cartItemService.getAllCartItems();
    return ResponseEntity.ok(cartItems);
  }

  @GetMapping("/{cartItemId}")
  public ResponseEntity<CartItemDTO> getCartItemById(@PathVariable CartItemPK cartItemId) {
    CartItemDTO cartItemDTO = cartItemService.getCartItemById(cartItemId);
    return ResponseEntity.ok(cartItemDTO);
  }

  @PostMapping
  public ResponseEntity<CartItemDTO> createCartItem(@Valid @RequestBody CartItemDTO cartItemDTO) {
    CartItemDTO createdCartItem = cartItemService.createCartItem(cartItemDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{cartItemId}")
        .buildAndExpand(createdCartItem.cartItemId())
        .toUri();
    return ResponseEntity.created(location).body(createdCartItem);
  }

  @PutMapping("/{cartItemId}")
  public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable CartItemPK cartItemId,
      @Valid @RequestBody CartItemDTO cartItemDTO) {
    CartItemDTO updatedCartItem = cartItemService.updateCartItem(cartItemId, cartItemDTO);
    return ResponseEntity.ok(updatedCartItem);
  }

  @DeleteMapping("/{cartItemId}")
  public ResponseEntity<Void> deleteCartItem(@PathVariable CartItemPK cartItemId) {
    cartItemService.deleteCartItem(cartItemId);
    return ResponseEntity.noContent().build();
  }
}