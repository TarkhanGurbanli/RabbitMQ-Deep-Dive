package com.tarkhangurbanli.rabbitmqdeepdive.service.event.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange.book}")
    private String bookExchange;

    @Value("${app.rabbitmq.exchange.author}")
    private String authorExchange;

    public void publishBook(Object message, String routingKey) {
        rabbitTemplate.convertAndSend(bookExchange, routingKey, message);
    }

    public void publishAuthor(Object message, String routingKey) {
        rabbitTemplate.convertAndSend(authorExchange, routingKey, message);
    }
}
