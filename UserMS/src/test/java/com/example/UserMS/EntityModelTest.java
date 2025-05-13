
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
        StudentsEntity student = new StudentsEntity("student@example.com", "Student Name", "password123");
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
        student.setId(42L);
        assertEquals(42L, student.getId());
    }

    // InstructorsEntity tests
    @Test
    void testInstructorConstructorAndGetters() {
        InstructorsEntity instructor = new InstructorsEntity("instpass", "inst@guc.edu.eg", "Instructor");
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
        instructor.setId(5L);
        assertEquals(5L, instructor.getId());
    }

    // AdminsEntity tests
    @Test
    void testAdminConstructorAndGetters() {
        AdminsEntity admin = new AdminsEntity("adminpass", "admin@guc.edu.eg", "Admin");
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
        admin.setId(1L);
        assertEquals(1L, admin.getId());
    }



    @Test
    void testSetNullEmail() {
        StudentsEntity student = new StudentsEntity();
        student.setEmail(null);
        assertNull(student.getEmail());
    }
}
