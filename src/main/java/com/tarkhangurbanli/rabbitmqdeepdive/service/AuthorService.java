package com.tarkhangurbanli.rabbitmqdeepdive.service;

import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.request.AuthorRequestDto;
import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.response.Response;

public interface AuthorService {

    Response create(AuthorRequestDto request);

    Response update(Long id, AuthorRequestDto request);

    Response delete(Long id);

}
