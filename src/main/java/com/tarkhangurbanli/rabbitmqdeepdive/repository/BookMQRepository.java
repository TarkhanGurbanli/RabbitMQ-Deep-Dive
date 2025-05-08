package com.tarkhangurbanli.rabbitmqdeepdive.repository;

import com.tarkhangurbanli.rabbitmqdeepdive.model.entity.BookMQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMQRepository extends JpaRepository<BookMQ, Long> {
}
