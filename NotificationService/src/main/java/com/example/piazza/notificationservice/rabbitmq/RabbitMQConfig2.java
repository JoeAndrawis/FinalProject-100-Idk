package com.example.piazza.notificationservice.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig2 {

    public static final String EXCHANGE = "question-events-exchange";  // Same as QuestionMS
    public static final String QUEUE = "notifyPosting_queue";          // Same queue name
    public static final String ROUTING_KEY = "notifyPosting_routing_key"; // Same routing key

    @Bean(name = "notifyPostingQueue")
    public Queue notifyPostingQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean(name = "notifyPostingExchange")
    public Exchange notifyPostingExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding notifyPostingBinding(@Qualifier("notifyPostingQueue") Queue queue,
                                        @Qualifier("notifyPostingExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY).noargs();
    }

    @Bean
    public MessageConverter jsonMessageConverter2() {
        return new Jackson2JsonMessageConverter();
    }
}
