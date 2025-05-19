package com.example.QuestionMS.rabbitmq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig2 {

    public static final String NOTIFYPOSTING_QUEUE = "notifyPosting_queue";
    public static final String EXCHANGE = "shared_exchange";
    public static final String NOTIFYPOSTING_ROUTING_KEY = "notifyPosting_routing_key";

    @Bean
    public Queue notifyPostingQueue() {
        return new Queue(NOTIFYPOSTING_QUEUE);
    }

    @Bean
    public TopicExchange notifyPostingExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding notifyPostingBinding(Queue notifyPostingQueue, TopicExchange notifyPostingExchange) {
        return BindingBuilder
                .bind(notifyPostingQueue)
                .to(notifyPostingExchange)
                .with(NOTIFYPOSTING_ROUTING_KEY);
    }


}
