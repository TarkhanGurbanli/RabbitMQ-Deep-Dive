package com.tarkhangurbanli.rabbitmqdeepdive.evet.listener;

import com.tarkhangurbanli.rabbitmqdeepdive.model.entity.Author;
import com.tarkhangurbanli.rabbitmqdeepdive.model.entity.AuthorMQ;
import com.tarkhangurbanli.rabbitmqdeepdive.repository.AuthorMQRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorConsumer {

    private final AuthorMQRepository authorMQRepository;

    @RabbitListener(queues = "${app.rabbitmq.queue.author}")
    public void consumeAuthor(Author author) {
        try {
            AuthorMQ authorMQ = new AuthorMQ(author.getId(), author.getName());
            authorMQRepository.save(authorMQ);
            log.info("Author RabbitMQ saved: {}", authorMQ);
        } catch (Exception e) {
            log.error("Error processing author message: {}", author, e);
        }
    }

}