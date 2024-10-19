package com.projeto.ReFood.service;

import com.projeto.ReFood.repository.ContactRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.ContactDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.Contact;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ContactService {

  @Autowired
  private ContactRepository contactRepository;

  @Autowired
  private UtilityService utilityService;

  @Transactional(readOnly = true)
  public List<ContactDTO> getAllContacts() {
    return contactRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ContactDTO getContactById(Long contactId) {
    return contactRepository.findById(contactId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public ContactDTO createContact(@Valid ContactDTO contactDTO) {
    Contact contact = convertToEntity(contactDTO);
    utilityService.associateRestaurant(contact::setRestaurant, contactDTO.restaurantId());
    return convertToDTO(contactRepository.save(contact));
  }

  @Transactional
  public ContactDTO updateContact(Long contactId, @Valid ContactDTO contactDTO) {
    Contact contact = contactRepository.findById(contactId)
        .orElseThrow(() -> new NotFoundException());

    contact.setDescription(contactDTO.description());
    contact.setPhone(contactDTO.phone());
    utilityService.associateRestaurant(contact::setRestaurant, contactDTO.restaurantId());

    return convertToDTO(contactRepository.save(contact));
  }

  @Transactional
  public void deleteContact(Long contactId) {
    if (!contactRepository.existsById(contactId)) {
      throw new NotFoundException();
    }
    contactRepository.deleteById(contactId);
  }

  private ContactDTO convertToDTO(Contact contact) {
    return new ContactDTO(
        contact.getContactId(),
        contact.getDescription(),
        contact.getPhone(),
        contact.getRestaurant().getRestaurantId());
  }

  private Contact convertToEntity(ContactDTO contactDTO) {
    Contact contact = new Contact();
    contact.setContactId(contactDTO.contactId());
    contact.setDescription(contactDTO.description());
    contact.setPhone(contactDTO.phone());

    return contact;
  }
}
