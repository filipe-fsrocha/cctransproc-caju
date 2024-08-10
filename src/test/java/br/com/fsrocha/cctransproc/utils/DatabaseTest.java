package br.com.fsrocha.cctransproc.utils;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("test")
public abstract class DatabaseTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    DataSource dataSource;

    @Autowired
    Flyway flyway;

    @BeforeEach
    public void setUp() {
        try (Connection conn = dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DROP ALL OBJECTS");
                stmt.execute("CREATE SCHEMA test");
                stmt.execute("SET SCHEMA test");
            }

            flyway.migrate();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
