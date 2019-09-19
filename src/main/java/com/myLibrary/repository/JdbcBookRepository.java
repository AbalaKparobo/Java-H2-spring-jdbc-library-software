package com.myLibrary.repository;


import com.myLibrary.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class JdbcBookRepository implements BookRepository {

    // Spring Boot will create and configure DataSource and JdbcTemplate
    // To use it, just @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from books", Integer.class);
    }

    @Override
    public int save(Book book) {
        return jdbcTemplate.update("insert into books (title, description, " +
                "isbn, unitcost) values(?, ?, ?, ?)", book.getTitle(),
                book.getDescription(), book.getIsbn(),
                book.getUnitCost());
    }

    @Override
    public int update(Book book) {
        return jdbcTemplate.update("update books set unitCost = ?, title " +
                        "= ?, description = ?, isbn = ? where id = ?",
                book.getUnitCost(), book.getTitle(), book.getDescription(),
                book.getIsbn(), book.getId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("delete books where id = ?", id);
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("Select * from books",
                (rs, rowNum) -> new Book(rs.getLong("id"),
                        rs.getString("title"), rs.getString("description"), rs.getString("isbn"),
                        rs.getFloat("unitCost")));
    }

    @Override
    public Optional<Book> findById(Long id) {
        return jdbcTemplate.queryForObject("select * from books where id = ?"
                , new Object[] {id}, (rs, rowNum) -> Optional.of(new Book(rs.getLong("id"),
                        rs.getString("title"), rs.getString("description"), rs.getString("isbn"),
                        rs.getFloat("unitCost")
                        )
                ));
    }

    @Override
    public List<Book> findByNameAndPrice(String title, float unitCost) {
        return jdbcTemplate.query("select * from books where title like ? and" +
                " unitCost <= ?", new Object[] {"%" + title + "%", unitCost},
                (rs,
                rowNum) -> new Book(rs.getLong("id"),
                rs.getString("title"), rs.getString("description"), rs.getString("isbn"),
                rs.getFloat("unitCost")
        ));
    }

    @Override
    public String getNameById(Long id) {
        return jdbcTemplate.queryForObject("select title from books where id = ?", new Object[]{id}, String.class);
    }

}
