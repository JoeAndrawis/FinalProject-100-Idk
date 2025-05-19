package com.example.piazza.notificationservice.rabbitmq;

import com.example.piazza.notificationservice.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.example.piazza.notificationservice.dto.QuestionEvent;
import com.example.piazza.notificationservice.repository.NotificationRepository;

@Component
public class QuestionEventListener {
    private final NotificationRepository notificationRepository;

    public QuestionEventListener( NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;

    }

    @RabbitListener(queues = RabbitMQConfig2.QUEUE)
    public void handleQuestionEvent(QuestionEvent event) {
        String msg;
        Notification notification = new Notification();
        System.out.println("📬 Notification saved: " + event.getEventType());

        switch (event.getEventType()) {
            case "QUESTION_CREATED":
                msg = "A new question was created (ID: " + event.getQuestionId() + ")";
                notification.setMessage(msg);
                notification.setUserId(event.getQuestionId());

                notificationRepository.save(notification);
                System.out.println("📬 Notification saved: " + msg);
                break;
            case "QUESTION_UPDATED":
                msg = "Question updated (ID: " + event.getQuestionId() + ")";
                notification.setMessage(msg);
                notification.setUserId(event.getQuestionId());
                notificationRepository.save(notification);
                System.out.println("📬 Notification saved: " + msg);
                break;
            case "QUESTION_DELETED":
                msg = "Question deleted (ID: " + event.getQuestionId() + ")";
                notification.setMessage(msg);
                notification.setUserId(event.getQuestionId());
                notificationRepository.save(notification);
                System.out.println("📬 Notification saved: " + msg);
                break;
            case "QUESTION_ANSWER_ADDED":
                msg = "New answer added to question (ID: " + event.getQuestionId() + ")";
                notification.setMessage(msg);
                notification.setUserId(event.getQuestionId());
                notificationRepository.save(notification);
                System.out.println("📬 Notification saved: " + msg);
                break;
            default:
                msg = "Question event: " + event.getEventType();

        }


    }
}

