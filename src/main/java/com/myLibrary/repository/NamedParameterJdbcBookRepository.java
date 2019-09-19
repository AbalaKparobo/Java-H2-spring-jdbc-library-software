package com.myLibrary.repository;

import com.myLibrary.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class NamedParameterJdbcBookRepository extends JdbcBookRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int update(Book book) {
        return namedParameterJdbcTemplate.update("update books set unitCost =" +
                " :unitCost where id = :id",
                new BeanPropertySqlParameterSource(book));
    }

    @Override
    public Optional<Book> findById(Long id ) {
        return namedParameterJdbcTemplate.queryForObject("select * from books" +
                " where id = :id", new MapSqlParameterSource("id", id), (rs,
                rowNum) -> Optional.of(new Book(rs.getLong("id"),
                        rs.getString("title"), rs.getString("description"), rs.getString("isbn"),
                        rs.getFloat("unitCost")
                ))
        );
    }

    @Override
    public List<Book> findByNameAndPrice(String name, float price) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", "%" + name + "%");
        mapSqlParameterSource.addValue("price", price);

        return namedParameterJdbcTemplate.query("select * from books where " +
                "title like :name and unitCost like :price",
                mapSqlParameterSource, (rs, rowNum) -> new Book(rs.getLong("id"),
                        rs.getString("title"), rs.getString("description"), rs.getString("isbn"),
                        rs.getFloat("unitCost")
                )
        );
    }

}
