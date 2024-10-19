package com.projeto.ReFood.controller;

import com.projeto.ReFood.service.NotificationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.ReFood.dto.NotificationDTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @GetMapping
  public ResponseEntity<List<NotificationDTO>> listAllNotifications() {
    List<NotificationDTO> notifications = notificationService.getAllNotifications();
    return ResponseEntity.ok(notifications);
  }

  @GetMapping("/{notificationId}")
  public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long notificationId) {
    NotificationDTO notificationDTO = notificationService.getNotificationById(notificationId);
    return ResponseEntity.ok(notificationDTO);
  }

  @PostMapping
  public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody NotificationDTO notificationDTO) {
    NotificationDTO createdNotification = notificationService.createNotification(notificationDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{notificationId}")
        .buildAndExpand(createdNotification.notificationId())
        .toUri();
    return ResponseEntity.created(location).body(createdNotification);
  }

  @PutMapping("/{notificationId}")
  public ResponseEntity<NotificationDTO> updateNotification(@PathVariable Long notificationId,
      @Valid @RequestBody NotificationDTO notificationDTO) {
    NotificationDTO updatedNotification = notificationService.updateNotification(notificationId, notificationDTO);
    return ResponseEntity.ok(updatedNotification);
  }

  @DeleteMapping("/{notificationId}")
  public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
    notificationService.deleteNotification(notificationId);
    return ResponseEntity.noContent().build();
  }
}
