package com.projeto.ReFood.controller;

import com.projeto.ReFood.dto.CartDTO;
import com.projeto.ReFood.service.CartService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @GetMapping
  public ResponseEntity<List<CartDTO>> listAllCarts() {
    List<CartDTO> carts = cartService.getAllCarts();
    return ResponseEntity.ok(carts);
  }

  @GetMapping("/{cartId}")
  public ResponseEntity<CartDTO> getCartById(@PathVariable Long cartId) {
    CartDTO cartDTO = cartService.getCartById(cartId);
    return ResponseEntity.ok(cartDTO);
  }

  @PostMapping
  public ResponseEntity<CartDTO> createCart(@Valid @RequestBody CartDTO cartDTO) {
    CartDTO createdCart = cartService.createCart(cartDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{cartId}")
        .buildAndExpand(createdCart.cartId())
        .toUri();
    return ResponseEntity.created(location).body(createdCart);
  }

  @PutMapping("/{cartId}")
  public ResponseEntity<CartDTO> updateCart(@PathVariable Long cartId, @Valid @RequestBody CartDTO cartDTO) {
    CartDTO updatedCart = cartService.updateCart(cartId, cartDTO);
    return ResponseEntity.ok(updatedCart);
  }

  @DeleteMapping("/{cartId}")
  public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
    cartService.deleteCart(cartId);
    return ResponseEntity.noContent().build();
  }
}
