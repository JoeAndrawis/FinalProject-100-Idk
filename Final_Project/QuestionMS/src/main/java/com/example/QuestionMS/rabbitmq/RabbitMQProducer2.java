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
                RabbitMQConfig2.EXCHANGE,
                RabbitMQConfig2.NOTIFYPOSTING_ROUTING_KEY,
                message
        );
        System.out.println("Post Notification Queue: " + message);
    }
}

