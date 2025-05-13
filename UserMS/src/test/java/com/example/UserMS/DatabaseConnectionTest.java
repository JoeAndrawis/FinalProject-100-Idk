package com.example.UserMS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class DatabaseConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testPostgreSQLConnection() {
        try {
            String dbVersion = jdbcTemplate.queryForObject("SELECT version()", String.class);
            assertNotNull(dbVersion);
        } catch (Exception e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @Test
    public void testStudentTableExists() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM students", Integer.class);
        assertNotNull(count);
    }
}