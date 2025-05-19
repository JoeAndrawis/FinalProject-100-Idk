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
    public Queue NotifyPostingQueue() {
        return new Queue(NOTIFYPOSTING_QUEUE);
    }

    @Bean
    public TopicExchange NotifyPostingExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding NotifyPostingBean(Queue NotifyPostingQueue, TopicExchange NotifyPostingExchange) {
        return BindingBuilder
                .bind(NotifyPostingQueue)
                .to(NotifyPostingExchange)
                .with(NOTIFYPOSTING_ROUTING_KEY);
    }


}
