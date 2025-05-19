package com.example.piazza.notificationservice.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "shared_exchange";  // same as UserMS
    public static final String QUEUE = "notification-queue";  // same as UserMS
    public static final String ROUTING_KEY = "user.notify";   // same as UserMS

    @Bean(name = "notificationQueue")
    public Queue notificationQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean(name = "notificationExchange")
    public Exchange notificationExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding notificationBinding(@Qualifier("notificationQueue") Queue queue,
                                       @Qualifier("notificationExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY).noargs();
    }

    // JSON Message converter bean
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
