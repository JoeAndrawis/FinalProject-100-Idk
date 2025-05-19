package com.example.UserMS;

import com.example.UserMS.Models.AdminsEntity;
import com.example.UserMS.Models.InstructorsEntity;
import com.example.UserMS.Models.StudentsEntity;
import com.example.UserMS.Repository.AdminRepository;
import com.example.UserMS.Repository.InstructorRepository;
import com.example.UserMS.Repository.StudentRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {

    // Admin Tests
    @Test
    void testAdminRepositoryFindByEmailFound() {
        AdminRepository repo = mock(AdminRepository.class);
        AdminsEntity admin = new AdminsEntity();
        admin.setEmail("admin@guc.edu.eg");
        admin.setName("Admin");
        when(repo.findByEmail("admin@guc.edu.eg")).thenReturn(Optional.of(admin));

        assertTrue(repo.findByEmail("admin@guc.edu.eg").isPresent());
    }

    @Test
    void testAdminRepositoryFindByEmailNotFound() {
        AdminRepository repo = mock(AdminRepository.class);
        when(repo.findByEmail("missing@guc.edu.eg")).thenReturn(Optional.empty());

        assertFalse(repo.findByEmail("missing@guc.edu.eg").isPresent());
    }

    @Test
    void testAdminRepositoryFindByName() {
        AdminRepository repo = mock(AdminRepository.class);
        AdminsEntity admin = new AdminsEntity();
        admin.setName("Admin");
        when(repo.findByName("Admin")).thenReturn(Optional.of(admin));

        assertEquals("Admin", repo.findByName("Admin").get().getName());
    }

    // Instructor Tests
    @Test
    void testInstructorRepositoryFindByEmailFound() {
        InstructorRepository repo = mock(InstructorRepository.class);
        InstructorsEntity instructor = new InstructorsEntity();
        instructor.setEmail("inst@guc.edu.eg");
        when(repo.findByEmail("inst@guc.edu.eg")).thenReturn(Optional.of(instructor));

        assertTrue(repo.findByEmail("inst@guc.edu.eg").isPresent());
    }

    @Test
    void testInstructorRepositoryFindByEmailNotFound() {
        InstructorRepository repo = mock(InstructorRepository.class);
        when(repo.findByEmail("notfound@guc.edu.eg")).thenReturn(Optional.empty());

        assertFalse(repo.findByEmail("notfound@guc.edu.eg").isPresent());
    }

    @Test
    void testInstructorRepositoryFindByName() {
        InstructorRepository repo = mock(InstructorRepository.class);
        InstructorsEntity instructor = new InstructorsEntity();
        instructor.setName("Instructor");
        when(repo.findByName("Instructor")).thenReturn(Optional.of(instructor));

        assertEquals("Instructor", repo.findByName("Instructor").get().getName());
    }

    // Student Tests
    @Test
    void testStudentRepositoryFindByEmailFound() {
        StudentRepository repo = mock(StudentRepository.class);
        StudentsEntity student = new StudentsEntity();
        student.setEmail("student@guc.edu.eg");
        when(repo.findByEmail("student@guc.edu.eg")).thenReturn(Optional.of(student));

        assertTrue(repo.findByEmail("student@guc.edu.eg").isPresent());
    }

    @Test
    void testStudentRepositoryFindByEmailNotFound() {
        StudentRepository repo = mock(StudentRepository.class);
        when(repo.findByEmail("missing@guc.edu.eg")).thenReturn(Optional.empty());

        assertFalse(repo.findByEmail("missing@guc.edu.eg").isPresent());
    }

    @Test
    void testStudentRepositoryFindByName() {
        StudentRepository repo = mock(StudentRepository.class);
        StudentsEntity student = new StudentsEntity();
        student.setName("Student");
        when(repo.findByName("Student")).thenReturn(Optional.of(student));

        assertEquals("Student", repo.findByName("Student").get().getName());
    }
}