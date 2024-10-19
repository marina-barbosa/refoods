package com.projeto.ReFood.controller;

import com.projeto.ReFood.service.AddressService;

import jakarta.validation.Valid;

import com.projeto.ReFood.dto.AddressDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

  @Autowired
  private AddressService addressService;

  @GetMapping
  public ResponseEntity<List<AddressDTO>> listAllAddresses() {
    List<AddressDTO> addresses = addressService.getAllAddresses();
    return ResponseEntity.ok(addresses);
  }

  @GetMapping("/{addressId}")
  public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
    AddressDTO addressDTO = addressService.getAddressById(addressId);
    return ResponseEntity.ok(addressDTO);
  }

  @PostMapping
  public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
    AddressDTO createdAddress = addressService.createAddress(addressDTO, null, null, null);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{addressId}")
        .buildAndExpand(createdAddress.addressId())
        .toUri();
    return ResponseEntity.created(location).body(createdAddress);
  }

  @PutMapping("/{addressId}")
  public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId,
      @Valid @RequestBody AddressDTO addressDTO)
      throws NotFoundException {
    AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
    return ResponseEntity.ok(updatedAddress);
  }

  @DeleteMapping("/{addressId}")
  public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
    addressService.deleteAddress(addressId);
    return ResponseEntity.noContent().build();
  }
}