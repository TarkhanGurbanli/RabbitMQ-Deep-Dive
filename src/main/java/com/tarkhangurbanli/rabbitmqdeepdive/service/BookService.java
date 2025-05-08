package com.tarkhangurbanli.rabbitmqdeepdive.service;

import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.request.BookRequestDto;
import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.response.Response;

public interface BookService {

    Response create(BookRequestDto request);

    Response update(Long id, BookRequestDto request);

    Response delete(Long id);

}
