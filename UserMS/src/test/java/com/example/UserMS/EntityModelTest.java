package com.example.UserMS;

import com.example.UserMS.Models.AdminsEntity;
import com.example.UserMS.Models.InstructorsEntity;
import com.example.UserMS.Models.StudentsEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityModelTest {

    // StudentsEntity tests
    @Test
    void testStudentConstructorAndGetters() {
        StudentsEntity student = new StudentsEntity("Student Name", "student@example.com", "password123");
        assertEquals("Student Name", student.getName());
        assertEquals("student@example.com", student.getEmail());
        assertEquals("password123", student.getPassword());
    }

    @Test
    void testStudentSetters() {
        StudentsEntity student = new StudentsEntity();
        student.setName("Ahmed");
        student.setEmail("ahmed@guc.edu.eg");
        student.setPassword("abc123");

        assertEquals("Ahmed", student.getName());
        assertEquals("ahmed@guc.edu.eg", student.getEmail());
        assertEquals("abc123", student.getPassword());
    }

    @Test
    void testStudentIdSetterGetter() {
        StudentsEntity student = new StudentsEntity();
        Long expectedId = 42L;
        student.setId(expectedId);
        assertEquals(expectedId, student.getId());
    }

    // InstructorsEntity tests
    @Test
    void testInstructorConstructorAndGetters() {
        InstructorsEntity instructor = new InstructorsEntity("Instructor", "inst@guc.edu.eg", "instpass");
        assertEquals("Instructor", instructor.getName());
        assertEquals("inst@guc.edu.eg", instructor.getEmail());
        assertEquals("instpass", instructor.getPassword());
    }

    @Test
    void testInstructorSetters() {
        InstructorsEntity instructor = new InstructorsEntity();
        instructor.setName("Dr. Omar");
        instructor.setEmail("omar@guc.edu.eg");
        instructor.setPassword("mypassword");

        assertEquals("Dr. Omar", instructor.getName());
        assertEquals("omar@guc.edu.eg", instructor.getEmail());
        assertEquals("mypassword", instructor.getPassword());
    }

    @Test
    void testInstructorIdSetterGetter() {
        InstructorsEntity instructor = new InstructorsEntity();
        Long expectedId = 5L;
        instructor.setId(expectedId);
        assertEquals(expectedId, instructor.getId());
    }

    // AdminsEntity tests
    @Test
    void testAdminConstructorAndGetters() {
        AdminsEntity admin = new AdminsEntity("Admin", "admin@guc.edu.eg", "adminpass");
        assertEquals("Admin", admin.getName());
        assertEquals("admin@guc.edu.eg", admin.getEmail());
        assertEquals("adminpass", admin.getPassword());
    }

    @Test
    void testAdminSetters() {
        AdminsEntity admin = new AdminsEntity();
        admin.setName("Head Admin");
        admin.setEmail("headadmin@guc.edu.eg");
        admin.setPassword("secure");

        assertEquals("Head Admin", admin.getName());
        assertEquals("headadmin@guc.edu.eg", admin.getEmail());
        assertEquals("secure", admin.getPassword());
    }

    @Test
    void testAdminIdSetterGetter() {
        AdminsEntity admin = new AdminsEntity();
        Long expectedId = 1L;
        admin.setId(expectedId);
        assertEquals(expectedId, admin.getId());
    }

    @Test
    void testSetNullEmail() {
        StudentsEntity student = new StudentsEntity();
        student.setEmail(null);
        assertNull(student.getEmail());
    }
}