package com.tarkhangurbanli.rabbitmqdeepdive.service.impl;

import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.request.AuthorRequestDto;
import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.response.Response;
import com.tarkhangurbanli.rabbitmqdeepdive.model.entity.Author;
import com.tarkhangurbanli.rabbitmqdeepdive.repository.AuthorRepository;
import com.tarkhangurbanli.rabbitmqdeepdive.service.AuthorService;
import com.tarkhangurbanli.rabbitmqdeepdive.service.event.publisher.PublisherService;
import com.tarkhangurbanli.rabbitmqdeepdive.util.ResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final PublisherService publisherService;
    private final ResponseFactory responseFactory;

    @Value("${app.rabbitmq.routing.author.created}")
    private String authorCreated;

    @Value("${app.rabbitmq.routing.author.updated}")
    private String authorUpdated;

    @Value("${app.rabbitmq.routing.author.deleted}")
    private String authorDeleted;

    public Response create(AuthorRequestDto request) {
        Author saved = authorRepository.save(new Author(request.name()));
        publisherService.publishAuthor(saved, authorCreated);
        return responseFactory.success("Author created successfully", HttpStatus.CREATED);
    }

    public Response update(Long id, AuthorRequestDto request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.setName(request.name());
        Author saved = authorRepository.save(author);
        publisherService.publishAuthor(saved, authorUpdated);
        return responseFactory.success("Author updated successfully", HttpStatus.OK);
    }

    public Response delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.delete(author);
        publisherService.publishAuthor(author, authorDeleted);
        return responseFactory.success("Author deleted successfully", HttpStatus.OK);
    }

}
