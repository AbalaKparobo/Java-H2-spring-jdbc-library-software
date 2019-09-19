package com.myLibrary;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.myLibrary.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class StartApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);
//    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
//    @Qualifier("jdbcBookRepository");
//    @Qualifier("namedParameterJdbcBookRepository");
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("StartApplication...");
        try {
            runJDBC();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    void runJDBC() throws Throwable {
        log.info("Creating table for testing");

        jdbcTemplate.execute("DROP TABLE books IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE books(" +
                        "id SERIAL, title VARCHAR(255), description VARCHAR(255), isbn VARCHAR(255), " +
                "unitCost NUMERIC(15, 2))");

        List<Book> books = Arrays.asList(
                new Book(1L, "Thinking in Java", "Thinking in Java Description",
                        "isbnthinkinginjava",
                        46.32f),
                    new Book(2L, "Library in Java", "Library in Java " +
                            "Description", "isbnlibraryinjava",1.99f),
                    new Book(3L, "Getting Clojure", "Getting Clojure " +
                            "Description"
                            , "isbngettingclojure", 37.3f),
                    new Book(4L, "Head First Android Development", "Head " +
                            "First Android Development Description",
                            "isbnheadfirstandroiddevelopment", 41.19f)
        );


                log.info("[SAVE]");
                books.forEach(book -> {
                    log.info("Saving...{}", book.getTitle());
                    bookRepository.save(book);
                });

                // Count
        log.info("[COUNT] Total books: {}", bookRepository.count());

        // findAll
        log.info("[Find All] {}", bookRepository.findAll());

        // Find by Id
        log.info("[FIND_BY_ID] :2L");
        Book book = bookRepository.findById(2L).orElseThrow(IllegalArgumentException::new);
        log.info("{}", book);

        //Update
//        log.info("[Update] :2L : price and title");
        book.setUnitCost(99.99f);
        book.setTitle("Java and SQL guru!!");
        log.info("rows affected: {}", bookRepository.update(book));

        // Get title by Id
        log.info("[Find Title by Id] :2L = {}", bookRepository.getNameById(2L));

        // Find by Title (like) and Price
        log.info("[Find by Title and Price] : like %Java% and unitCost <= 10");
        log.info("{}", bookRepository.findByNameAndPrice("Java", 10f));

        // Delete
        log.info("[Delete] :3L");
        log.info("rows affected: {}", bookRepository.deleteById(3L));

        //Find All
        log.info("[Find All] {}", bookRepository.findAll());

    }

}
