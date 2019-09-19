package com.myLibrary.repository;

import com.myLibrary.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository{
    int count();
    int save(Book book);
    int update(Book book);
    int deleteById(Long id);
    List<Book> findAll();
    List<Book> findByNameAndPrice(String name, float price);
    Optional<Book> findById(Long id);
    String getNameById(Long id);
}