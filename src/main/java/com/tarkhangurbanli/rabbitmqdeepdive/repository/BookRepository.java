package com.tarkhangurbanli.rabbitmqdeepdive.repository;

import com.tarkhangurbanli.rabbitmqdeepdive.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
