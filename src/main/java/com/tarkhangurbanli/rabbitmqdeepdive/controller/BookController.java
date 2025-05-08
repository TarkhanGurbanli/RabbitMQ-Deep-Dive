package com.tarkhangurbanli.rabbitmqdeepdive.controller;

import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.request.BookRequestDto;
import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.response.Response;
import com.tarkhangurbanli.rabbitmqdeepdive.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public Response create(@RequestBody BookRequestDto book) {
        return bookService.create(book);
    }

    @PutMapping("/{id}")
    public Response update(@PathVariable Long id, @RequestBody BookRequestDto book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        return bookService.delete(id);
    }

}
