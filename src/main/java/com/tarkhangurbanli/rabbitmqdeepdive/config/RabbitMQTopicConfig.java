package com.tarkhangurbanli.rabbitmqdeepdive.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopicConfig {

    @Value("${app.rabbitmq.exchange.book}")
    private String bookExchange;

    @Value("${app.rabbitmq.exchange.author}")
    private String authorExchange;

    @Value("${app.rabbitmq.queue.book}")
    private String bookQueue;

    @Value("${app.rabbitmq.queue.author}")
    private String authorQueue;

    @Value("${app.rabbitmq.routing.book.created}")
    private String bookCreated;

    @Value("${app.rabbitmq.routing.book.updated}")
    private String bookUpdated;

    @Value("${app.rabbitmq.routing.book.deleted}")
    private String bookDeleted;

    @Value("${app.rabbitmq.routing.author.created}")
    private String authorCreated;

    @Value("${app.rabbitmq.routing.author.updated}")
    private String authorUpdated;

    @Value("${app.rabbitmq.routing.author.deleted}")
    private String authorDeleted;

    @Bean
    public TopicExchange bookExchange() {
        return new TopicExchange(bookExchange);
    }

    @Bean
    public TopicExchange authorExchange() {
        return new TopicExchange(authorExchange);
    }

    @Bean
    public Queue bookQueue() {
        return new Queue(bookQueue);
    }

    @Bean
    public Queue authorQueue() {
        return new Queue(authorQueue);
    }

    @Bean
    public Binding bookCreatedBinding() {
        return BindingBuilder.bind(bookQueue()).to(bookExchange()).with(bookCreated);
    }

    @Bean
    public Binding bookUpdatedBinding() {
        return BindingBuilder.bind(bookQueue()).to(bookExchange()).with(bookUpdated);
    }

    @Bean
    public Binding bookDeletedBinding() {
        return BindingBuilder.bind(bookQueue()).to(bookExchange()).with(bookDeleted);
    }

    @Bean
    public Binding authorCreatedBinding() {
        return BindingBuilder.bind(authorQueue()).to(authorExchange()).with(authorCreated);
    }

    @Bean
    public Binding authorUpdatedBinding() {
        return BindingBuilder.bind(authorQueue()).to(authorExchange()).with(authorUpdated);
    }

    @Bean
    public Binding authorDeletedBinding() {
        return BindingBuilder.bind(authorQueue()).to(authorExchange()).with(authorDeleted);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
