package com.example.piazza.notificationservice.rabbitmq;

import com.example.piazza.notificationservice.dto.UserEvent;
import com.example.piazza.notificationservice.model.Notification;
import com.example.piazza.notificationservice.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    private final NotificationRepository notificationRepository;

    public UserEventListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleUserEvent(UserEvent event) {
        System.out.println("📩 Received user event: " + event.getAction() + " for " + event.getEmail());

        String message;
        switch (event.getAction()) {
            case "created":
                message = "Welcome " + event.getName();
                break;
            case "logged_in":
                message = "Welcome back " + event.getName();
                break;
            case "updated":
                message = "Profile updated for " + event.getName();
                break;
            default:
                message = "Unknown event: " + event.getAction();
        }

        // Save notification to DB
        Notification notification = new Notification();
        notification.getUserId(event.getId());
        notification.setMessage(message);
        notificationRepository.save(notification);

        System.out.println("📬 Notification saved: " + message);
    }
}