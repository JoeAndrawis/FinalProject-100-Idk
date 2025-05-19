package com.example.UserMS.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String POSTING_QUEUE = "posting_queue";
    public static final String EXCHANGE = "shared_exchange";
    public static final String POSTING_ROUTING_KEY = "posting_routing_key";
    public static final String NOTIFY_QUEUE = "notification-queue";
    public static final String NOTIFY_ROUTING_KEY = "user.notify";

    @Bean
    public Queue postingQueue() {
        return new Queue(POSTING_QUEUE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFY_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding postingBinding() {
        return BindingBuilder
                .bind(postingQueue())
                .to(exchange())
                .with(POSTING_ROUTING_KEY);
    }

    @Bean
    public Binding notifyBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(exchange())
                .with(NOTIFY_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
