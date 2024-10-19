package com.projeto.ReFood.service;

import com.projeto.ReFood.repository.NotificationRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.projeto.ReFood.dto.NotificationDTO;
import com.projeto.ReFood.exception.GlobalExceptionHandler.NotFoundException;
import com.projeto.ReFood.model.Notification;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;
  @Autowired
  private UtilityService utilityService;

  @Transactional(readOnly = true)
  public List<NotificationDTO> getAllNotifications() {
    return notificationRepository
        .findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public NotificationDTO getNotificationById(Long notificationId) {
    return notificationRepository.findById(notificationId)
        .map(this::convertToDTO)
        .orElseThrow(() -> new NotFoundException());
  }

  @Transactional
  public NotificationDTO createNotification(@Valid NotificationDTO notificationDTO) {
    Notification notification = convertToEntity(notificationDTO);
    utilityService.associateUser(notification::setUser, notificationDTO.userId());
    utilityService.associateRestaurant(notification::setRestaurant, notificationDTO.restaurantId());
    notification = notificationRepository.save(notification);
    return convertToDTO(notification);
  }

  @Transactional
  public NotificationDTO updateNotification(Long notificationId, @Valid NotificationDTO notificationDTO) {
    Notification notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> new NotFoundException());

    notification.setMsgNotification(notificationDTO.msgNotification());
    notification.setMsgRead(notificationDTO.msgRead());
    notification.setSendDate(notificationDTO.sendDate());

    utilityService.associateUser(notification::setUser, notificationDTO.userId());
    utilityService.associateRestaurant(notification::setRestaurant, notificationDTO.restaurantId());

    notification = notificationRepository.save(notification);
    return convertToDTO(notification);
  }

  @Transactional
  public void deleteNotification(Long notificationId) {
    if (!notificationRepository.existsById(notificationId)) {
      throw new NotFoundException();
    }
    notificationRepository.deleteById(notificationId);
  }

  private NotificationDTO convertToDTO(Notification notification) {
    return new NotificationDTO(
        notification.getNotificationId(),
        notification.getMsgNotification(),
        notification.isMsgRead(),
        notification.getSendDate(),
        notification.getUser().getUserId(),
        notification.getRestaurant().getRestaurantId());
  }

  private Notification convertToEntity(NotificationDTO notificationDTO) {
    Notification notification = new Notification();
    notification.setNotificationId(notificationDTO.notificationId());
    notification.setMsgNotification(notificationDTO.msgNotification());
    notification.setMsgRead(notificationDTO.msgRead());
    notification.setSendDate(notificationDTO.sendDate());
    utilityService.associateUser(notification::setUser, notificationDTO.userId());
    utilityService.associateRestaurant(notification::setRestaurant, notificationDTO.restaurantId());
    return notification;
  }
}