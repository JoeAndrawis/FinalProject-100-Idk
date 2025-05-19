package com.example.piazza.notificationservice.service;

import com.example.piazza.notificationservice.model.Notification;
import com.example.piazza.notificationservice.rabbitmq.RabbitMQConfig2;
import com.example.piazza.notificationservice.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public List<Notification> getAll() {
        return repo.findAll();
    }

    public Optional<Notification> getById(Long id) {
        return repo.findById(id);
    }

    public Notification create(Notification notification) {
        return repo.save(notification);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Notification markAsRead(Long id) {
        Notification notif = repo.findById(id).orElseThrow();
        notif.setRead(true);

        return repo.save(notif);
    }

    public Notification archive(Long id) {
        Notification notif = repo.findById(id).orElseThrow();
        notif.setArchived(true);
        return repo.save(notif);
    }

    public List<Notification> filter(String userId, String type, Boolean read) {
        return repo.findByUserIdAndTypeAndRead(userId, type, read);
    }

}
