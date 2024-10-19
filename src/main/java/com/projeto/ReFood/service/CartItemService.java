package com.projeto.ReFood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.CartItemDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.CartItem;
import com.projeto.ReFood.model.CartItemPK;
import com.projeto.ReFood.repository.CartItemRepository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class CartItemService {

  @Autowired
  private CartItemRepository cartItemRepository;

  @Autowired
  private UtilityService utilityService;

  @Transactional(readOnly = true)
  public List<CartItemDTO> getAllCartItems() {
    return cartItemRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CartItemDTO getCartItemById(CartItemPK cartItemId) {
    CartItem cartItem = cartItemRepository.findById(cartItemId)
        .orElseThrow(() -> new NotFoundException());
    return convertToDTO(cartItem);
  }

  @Transactional
  public CartItemDTO createCartItem(@Valid CartItemDTO cartItemDTO) {
    CartItem cartItem = convertToEntity(cartItemDTO);
    cartItem = cartItemRepository.save(cartItem);
    return convertToDTO(cartItem);
  }

  @Transactional
  public CartItemDTO updateCartItem(CartItemPK cartItemId, @Valid CartItemDTO cartItemDTO) {
    CartItem cartItem = cartItemRepository.findById(cartItemId)
        .orElseThrow(() -> new NotFoundException());

    cartItem.setQuantity(cartItemDTO.quantity());
    cartItem.setUnitValue(cartItemDTO.unitValue());
    cartItem.setSubtotal(cartItemDTO.subtotal());
    utilityService.associateCart(cartItem::setCart, cartItemDTO.cartId());
    utilityService.associateProduct(cartItem::setProduct, cartItemDTO.productId());

    cartItem = cartItemRepository.save(cartItem);
    return convertToDTO(cartItem);
  }

  @Transactional
  public void deleteCartItem(CartItemPK cartItemId) {
    if (!cartItemRepository.existsById(cartItemId)) {
      throw new NotFoundException();
    }
    cartItemRepository.deleteById(cartItemId);
  }

  public CartItemDTO convertToDTO(CartItem cartItem) {
    return new CartItemDTO(
        cartItem.getCartItemId(),
        cartItem.getQuantity(),
        cartItem.getUnitValue(),
        cartItem.getSubtotal(),
        cartItem.getCart().getCartId(),
        cartItem.getProduct().getProductId());
  }

  public CartItem convertToEntity(CartItemDTO cartItemDTO) {
    CartItem cartItem = new CartItem();
    CartItemPK cartItemPK = new CartItemPK(cartItemDTO.cartId(), cartItemDTO.productId());
    cartItem.setCartItemId(cartItemPK);
    cartItem.setQuantity(cartItemDTO.quantity());
    cartItem.setUnitValue(cartItemDTO.unitValue());
    cartItem.setSubtotal(cartItemDTO.subtotal());
    utilityService.associateCart(cartItem::setCart, cartItemDTO.cartId());
    utilityService.associateProduct(cartItem::setProduct, cartItemDTO.productId());
    return cartItem;
  }

}
