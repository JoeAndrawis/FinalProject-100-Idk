package com.example.piazza.notificationservice.repository;

import com.example.piazza.notificationservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(String userId);
    List<Notification> findByType(String type);
    List<Notification> findByRead(boolean read);
    List<Notification> findByUserIdAndTypeAndRead(String userId, String type, boolean read);
}
