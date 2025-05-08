package com.tarkhangurbanli.rabbitmqdeepdive.util;

import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.response.DataResponse;
import com.tarkhangurbanli.rabbitmqdeepdive.model.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {

    public Response success(String message, HttpStatus status) {
        return new Response(message, status.value());
    }

    public Response error(String message, HttpStatus status) {
        return new Response(message, status.value());
    }

    public Response withData(String message, Object data, HttpStatus status) {
        return new DataResponse<>(message, status.value(), data);
    }

}