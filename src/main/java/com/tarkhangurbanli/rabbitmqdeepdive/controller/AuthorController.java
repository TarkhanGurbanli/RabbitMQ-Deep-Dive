package com.tarkhangurbanli.rabbitmqdeepdive.controller;

import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.request.AuthorRequestDto;
import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.response.Response;
import com.tarkhangurbanli.rabbitmqdeepdive.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public Response create(@RequestBody AuthorRequestDto author) {
        return authorService.create(author);
    }

    @PutMapping("/{id}")
    public Response update(@PathVariable Long id, @RequestBody AuthorRequestDto author) {
        return authorService.update(id, author);
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        return authorService.delete(id);
    }

}
