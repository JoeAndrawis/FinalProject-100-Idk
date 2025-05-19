package com.example.UserMS.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange:shared_exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey:user.notify}")
    private String routingKey;

    public void sendToPosting(String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.POSTING_ROUTING_KEY,
                message
        );
        System.out.println("Sent From user: " + message);
    }

    public void sendUserEvent(UserEvent userEvent) {
        rabbitTemplate.convertAndSend(exchange, routingKey, userEvent);
        System.out.println("Sent UserEvent: " + userEvent);
    }
}
