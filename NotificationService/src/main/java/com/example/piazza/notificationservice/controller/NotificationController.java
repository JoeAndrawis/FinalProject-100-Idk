package com.example.piazza.notificationservice.controller;

import com.example.piazza.notificationservice.model.Notification;
import com.example.piazza.notificationservice.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Notification> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Notification getById(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return service.create(notification);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable Long id) {
        return service.markAsRead(id);
    }

    @PutMapping("/{id}/archive")
    public Notification archive(@PathVariable Long id) {
        return service.archive(id);
    }

    @GetMapping("/filter")
    public List<Notification> filter(
            @RequestParam String userId,
            @RequestParam String type,
            @RequestParam Boolean read
    ) {
        return service.filter(userId, type, read);
    }
}
