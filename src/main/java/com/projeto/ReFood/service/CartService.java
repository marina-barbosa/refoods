package com.projeto.ReFood.service;

import com.projeto.ReFood.dto.CartDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.Cart;
import com.projeto.ReFood.model.EnumCartStatus;
import com.projeto.ReFood.repository.CartRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class CartService {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private UtilityService utilityService;

  @Transactional(readOnly = true)
  public List<CartDTO> getAllCarts() {
    return cartRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CartDTO getCartById(Long cartId) {
    return cartRepository.findById(cartId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public CartDTO createCart(@Valid CartDTO cartDTO) {
    Cart cart = convertToEntity(cartDTO);
    utilityService.associateUser(cart::setUser, cartDTO.userId());
    cart = cartRepository.save(cart);
    return convertToDTO(cart);
  }

  @Transactional
  public CartDTO updateCart(Long cartId, @Valid CartDTO cartDTO) {
    Cart cart = cartRepository.findById(cartId)
        .orElseThrow(() -> new NotFoundException());

    cart.setTotalValue(cartDTO.totalValue());
    cart.setStatus(EnumCartStatus.valueOf(cartDTO.status()));

    utilityService.associateUser(cart::setUser, cartDTO.userId());

    cart = cartRepository.save(cart);
    return convertToDTO(cart);
  }

  @Transactional
  public void deleteCart(Long cartId) {
    if (!cartRepository.existsById(cartId)) {
      throw new NotFoundException();
    }
    cartRepository.deleteById(cartId);
  }

  private CartDTO convertToDTO(Cart cart) {
    return new CartDTO(
        cart.getCartId(),
        cart.getStatus().name(),
        cart.getTotalValue(),
        cart.getUser().getUserId());
  }

  private Cart convertToEntity(CartDTO cartDTO) {
    Cart cart = new Cart();
    cart.setCartId(cartDTO.cartId());
    cart.setTotalValue(cartDTO.totalValue());
    cart.setStatus(EnumCartStatus.valueOf(cartDTO.status()));
    utilityService.associateUser(cart::setUser, cartDTO.userId());
    return cart;
  }
}
