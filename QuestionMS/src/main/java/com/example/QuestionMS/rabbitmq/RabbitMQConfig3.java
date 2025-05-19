package com.example.QuestionMS.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig3 {

    public static final String EXCHANGE = "question_exchange";
    public static final String CREATED_KEY = "question.created";
    public static final String DELETED_KEY = "question.deleted";
    public static final String UPDATED_KEY = "question.updated";
    public static final String ANSWER_KEY = "question.answer";
    public static final String Question_Queue = "notifyPosting_queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue notifyPostingQueue() {
        return new Queue(Question_Queue);
    }

    @Bean
    public Binding bindingNotifyPostingQueue(Queue notifyPostingQueue, TopicExchange exchange) {
        return BindingBuilder.bind(notifyPostingQueue).to(exchange).with("notifyPosting_routing_key");
    }
}
