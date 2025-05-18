package com.example.piazza.notificationservice.service;

import com.example.piazza.notificationservice.model.Notification;
import com.example.piazza.notificationservice.repository.NotificationRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "notifications", key = "'all'")
    public List<Notification> getAll() {
        return repo.findAll();
    }

    @Cacheable(value = "notifications", key = "#id")
    public Optional<Notification> getById(Long id) {
        return repo.findById(id);
    }

    @CachePut(value = "notifications", key = "#result.id")
    public Notification create(Notification notification) {
        return repo.save(notification);
    }

    @CacheEvict(value = "notifications", allEntries = true)
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @CachePut(value = "notifications", key = "#id")
    public Notification markAsRead(Long id) {
        Notification notif = repo.findById(id).orElseThrow();
        notif.setRead(true);
        return repo.save(notif);
    }

    @CachePut(value = "notifications", key = "#id")
    public Notification archive(Long id) {
        Notification notif = repo.findById(id).orElseThrow();
        notif.setArchived(true);
        return repo.save(notif);
    }

    @Cacheable(value = "notifications", key = "'filter:' + #userId + ':' + #type + ':' + #read")
    public List<Notification> filter(String userId, String type, Boolean read) {
        return repo.findByUserIdAndTypeAndRead(userId, type, read);
    }
}
