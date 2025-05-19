package com.example.QuestionMS.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer2 {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendToNotifyPosting(String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig3.EXCHANGE,
                RabbitMQConfig3.CREATED_KEY,  // or UPDATED_KEY, DELETED_KEY, ANSWER_KEY as needed
                message
        );
        System.out.println("Post Notification Queue: " + message);
    }
}
