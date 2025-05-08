package com.tarkhangurbanli.rabbitmqdeepdive.repository;

import com.tarkhangurbanli.rabbitmqdeepdive.model.entity.AuthorMQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorMQRepository extends JpaRepository<AuthorMQ, Long> {
}
