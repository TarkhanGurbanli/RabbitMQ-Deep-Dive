package com.tarkhangurbanli.rabbitmqdeepdive.evet.listener;

import com.tarkhangurbanli.rabbitmqdeepdive.model.entity.Book;
import com.tarkhangurbanli.rabbitmqdeepdive.model.entity.BookMQ;
import com.tarkhangurbanli.rabbitmqdeepdive.repository.BookMQRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookConsumer {

    private final BookMQRepository bookMQRepository;

    @RabbitListener(queues = "${app.rabbitmq.queue.book}")
    public void consumeAuthor(Book book) {
        try {
            BookMQ bookMQ = new BookMQ(book.getId(), book.getName());
            bookMQRepository.save(bookMQ);
            log.info("Book RabbitMQ saved: {}", bookMQ);
        } catch (Exception e) {
            log.error("Error processing author message: {}", book, e);
        }
    }

}
