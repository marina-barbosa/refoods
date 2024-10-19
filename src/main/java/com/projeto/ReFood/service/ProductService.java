package com.projeto.ReFood.service;

import com.projeto.ReFood.repository.ProductRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.ProductDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UtilityService utilityService;

  @Transactional(readOnly = true)
  public List<ProductDTO> getAllProducts() {
    return productRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ProductDTO getProductById(Long productId) {
    return productRepository.findById(productId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public ProductDTO createProduct(@Valid ProductDTO productDTO) {
    Product product = convertToEntity(productDTO);
    utilityService.associateRestaurant(product::setRestaurant, productDTO.restaurantId());
    product = productRepository.save(product);
    return convertToDTO(product);
  }

  @Transactional
  public ProductDTO updateProduct(Long productId, @Valid ProductDTO productDTO) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new NotFoundException());

    product.setNameProduct(productDTO.nameProd());
    product.setDescriptionProduct(productDTO.descriptionProd());
    product.setUrlImgProduct(productDTO.urlImgProd());
    product.setValueProduct(productDTO.valueProd());
    product.setDiscount(productDTO.discount());
    product.setAdditionDate(productDTO.additionDate());
    product.setActive(productDTO.active());

    utilityService.associateRestaurant(product::setRestaurant, productDTO.restaurantId());

    product = productRepository.save(product);
    return convertToDTO(product);
  }

  @Transactional
  public void deleteProduct(Long productId) {
    if (!productRepository.existsById(productId)) {
      throw new NotFoundException();
    }
    productRepository.deleteById(productId);
  }

  private ProductDTO convertToDTO(Product product) {
    return new ProductDTO(
        product.getProductId(),
        product.getNameProduct(),
        product.getDescriptionProduct(),
        product.getUrlImgProduct(),
        product.getValueProduct(),
        product.getDiscount(),
        product.getExpirationDate(),
        product.getQuantity(),
        product.getCategoryProduct(),
        product.getAdditionDate(),
        product.isActive(),
        product.getRestaurant().getRestaurantId());
  }

  private Product convertToEntity(ProductDTO productDTO) {
    Product product = new Product();
    product.setProductId(productDTO.productId());
    product.setNameProduct(productDTO.nameProd());
    product.setDescriptionProduct(productDTO.descriptionProd());
    product.setUrlImgProduct(productDTO.urlImgProd());
    product.setValueProduct(productDTO.valueProd());
    product.setDiscount(productDTO.discount());
    product.setQuantity(productDTO.quantity());
    product.setExpirationDate(productDTO.expirationDate());
    product.setCategoryProduct(productDTO.categoryProduct());
    product.setAdditionDate(productDTO.additionDate());
    product.setActive(productDTO.active());
    utilityService.associateRestaurant(product::setRestaurant, productDTO.restaurantId());
    return product;
  }
}