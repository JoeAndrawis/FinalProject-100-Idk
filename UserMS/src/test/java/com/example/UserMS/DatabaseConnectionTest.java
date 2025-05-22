package com.example.UserMS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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
            System.out.println("Connected to PostgreSQL version: " + dbVersion);
        } catch (Exception e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @Test
    public void testStudentsTableExists() {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM students", Integer.class);
            assertNotNull(count);
        } catch (Exception e) {
            fail("Table 'students' not found or inaccessible.");
        }
    }

    @Test
    public void testAdminsTableExists() {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM admins", Integer.class);
            assertNotNull(count);
        } catch (Exception e) {
            fail("Table 'admins' not found or inaccessible.");
        }
    }

    @Test
    public void testInstructorsTableExists() {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM instructors", Integer.class);
            assertNotNull(count);
        } catch (Exception e) {
            fail("Table 'instructors' not found or inaccessible.");
        }
    }
}