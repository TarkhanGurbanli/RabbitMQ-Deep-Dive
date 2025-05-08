package com.tarkhangurbanli.rabbitmqdeepdive.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<T> extends Response {
    private T data;

    public DataResponse(String message, int code, T data) {
        super(message, code);
        this.data = data;
    }
}