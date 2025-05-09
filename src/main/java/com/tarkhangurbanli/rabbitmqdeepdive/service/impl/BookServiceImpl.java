package com.tarkhangurbanli.rabbitmqdeepdive.service.impl;

import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.request.BookRequestDto;
import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.response.Response;
import com.tarkhangurbanli.rabbitmqdeepdive.model.entity.Author;
import com.tarkhangurbanli.rabbitmqdeepdive.model.entity.Book;
import com.tarkhangurbanli.rabbitmqdeepdive.repository.AuthorRepository;
import com.tarkhangurbanli.rabbitmqdeepdive.repository.BookRepository;
import com.tarkhangurbanli.rabbitmqdeepdive.service.BookService;
import com.tarkhangurbanli.rabbitmqdeepdive.evet.publsiher.PublisherService;
import com.tarkhangurbanli.rabbitmqdeepdive.util.ResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherService publisherService;
    private final ResponseFactory responseFactory;

    @Value("${app.rabbitmq.routing.book.created}")
    private String bookCreated;

    @Value("${app.rabbitmq.routing.book.updated}")
    private String bookUpdated;

    @Value("${app.rabbitmq.routing.book.deleted}")
    private String bookDeleted;

    @Override
    public Response create(BookRequestDto request) {
        Author author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Book saved = bookRepository.save(new Book(request.name(), author));
        publisherService.publishBook(saved, bookCreated);
        return responseFactory.success("Book created successfully", HttpStatus.CREATED);
    }

    @Override
    public Response update(Long id, BookRequestDto request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Author author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        book.setName(request.name());
        book.setAuthor(author);
        Book updated = bookRepository.save(book);
        publisherService.publishBook(updated, bookUpdated);

        return responseFactory.success("Book updated successfully", HttpStatus.OK);
    }

    @Override
    public Response delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
        publisherService.publishBook(book, bookDeleted);
        return responseFactory.success("Book deleted successfully", HttpStatus.OK);
    }

}