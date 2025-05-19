package com.example.QuestionMS.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String POSTING_QUEUE = "posting_queue";
    public static final String EXCHANGE = "shared_exchange";
    public static final String POSTING_ROUTING_KEY = "posting_routing_key";

    @Bean
    public Queue PostingQueue() {
        return new Queue(POSTING_QUEUE);
    }

    @Bean
    public TopicExchange PostingExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding PostingBean(Queue PostingQueue, TopicExchange PostingExchange) {
        return BindingBuilder
                .bind(PostingQueue)
                .to(PostingExchange)
                .with(POSTING_ROUTING_KEY);
    }

    // Add a Jackson2JsonMessageConverter bean to serialize/deserialize JSON messages
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Configure RabbitTemplate to use the JSON message converter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
