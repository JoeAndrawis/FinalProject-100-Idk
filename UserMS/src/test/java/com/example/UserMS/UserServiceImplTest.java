package com.example.UserMS;

import com.example.UserMS.Models.AdminsEntity;
import com.example.UserMS.Models.InstructorsEntity;
import com.example.UserMS.Models.StudentsEntity;
import com.example.UserMS.Repository.AdminRepository;
import com.example.UserMS.Repository.InstructorRepository;
import com.example.UserMS.Repository.StudentRepository;
import com.example.UserMS.Service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private AdminRepository adminRepository;
    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ---------- Student Tests ----------
    @Test
    void testSignUpStudent_Success() {
        StudentsEntity student = new StudentsEntity("1234", "moamen@student.guc.edu.eg", "Moamen");
        when(studentRepository.save(student)).thenReturn(student);
        assertEquals(student, userService.signUp(student, "student"));
    }

    @Test
    void testUpdateStudentProfile_Found() {
        StudentsEntity student = new StudentsEntity("pass", "stud@guc.edu.eg", "Mido");
        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);
        assertEquals(student, userService.updateProfile(student, "student"));
    }

    @Test
    void testUpdateStudentProfile_NotFound() {
        StudentsEntity student = new StudentsEntity("pass", "none@guc.edu.eg", "Nada");
        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Optional.empty());
        assertNull(userService.updateProfile(student, "student"));
    }

    // ---------- Admin Tests ----------
    @Test
    void testSignUpAdmin() {
        AdminsEntity admin = new AdminsEntity("1234", "admin@guc.edu.eg", "Admin");
        when(adminRepository.save(admin)).thenReturn(admin);
        assertEquals(admin, userService.signUp(admin, "admin"));
    }

    @Test
    void testUpdateAdminProfile_Found() {
        AdminsEntity admin = new AdminsEntity("4321", "admin@guc.edu.eg", "Admin" );
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));
        when(adminRepository.save(admin)).thenReturn(admin);
        assertEquals(admin, userService.updateProfile(admin, "admin"));
    }

    @Test
    void testUpdateAdminProfile_NotFound() {
        AdminsEntity admin = new AdminsEntity("4321", "ghost@guc.edu.eg", "Ghost" );
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.empty());
        assertNull(userService.updateProfile(admin, "admin"));
    }

    // ---------- Instructor Tests ----------
    @Test
    void testSignUpInstructor() {
        InstructorsEntity instructor = new InstructorsEntity("instpass", "inst@guc.edu.eg", "Instructor");
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        assertEquals(instructor, userService.signUp(instructor, "instructor"));
    }

    @Test
    void testUpdateInstructorProfile_Found() {
        InstructorsEntity instructor = new InstructorsEntity("pass", "inst@guc.edu.eg", "Instruct" );
        when(instructorRepository.findByEmail(instructor.getEmail())).thenReturn(Optional.of(instructor));
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        assertEquals(instructor, userService.updateProfile(instructor, "instructor"));
    }

    @Test
    void testUpdateInstructorProfile_NotFound() {
        InstructorsEntity instructor = new InstructorsEntity("pass", "missing@guc.edu.eg", "Ghost" );
        when(instructorRepository.findByEmail(instructor.getEmail())).thenReturn(Optional.empty());
        assertNull(userService.updateProfile(instructor, "instructor"));
    }

    // ---------- General Tests ----------
    @Test
    void testLogin_UserFound() {
        StudentsEntity student = new StudentsEntity("pw", "moamen@student.guc.edu.eg", "Moamen");
        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Optional.of(student));
        String result = userService.login(student.getEmail());
        assertTrue(result.contains("fake-token-for-"));
    }

    @Test
    void testLogin_NotFound() {
        String email = "notexist@guc.edu.eg";
        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(adminRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(instructorRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertEquals("User not found", userService.login(email));
    }

    @Test
    void testLogout() {
        userService.logout();
        assertNull(SessionManager.getInstance().getToken());
    }

    @Test
    void testViewProfile() {
        StudentsEntity student = new StudentsEntity("pw", "profile@guc.edu.eg", "Mido");
        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Optional.of(student));
        assertEquals(student, userService.viewProfile(student.getEmail()));
    }

    @Test
    void testVerifyUser_Found() {
        StudentsEntity student = new StudentsEntity("Karine", "exist@guc.edu.eg", "pw");
        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Optional.of(student));
        assertEquals("Student exists: Karine", userService.verifyUser(student.getEmail()));
    }

    @Test
    void testVerifyUser_NotFound() {
        String email = "noone@guc.edu.eg";
        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(adminRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(instructorRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertEquals("User not found!", userService.verifyUser(email));
    }

    @Test
    void testDeleteUser_Student() {
        String email = "del@guc.edu.eg";
        StudentsEntity student = new StudentsEntity();
        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));
        userService.deleteUser(email);
        verify(studentRepository).delete(student);
    }

    @Test
    void testFindUserByName_Student() {
        StudentsEntity student = new StudentsEntity("123", "email@guc.edu.eg", "Karine");
        when(studentRepository.findByName("Karine")).thenReturn(Optional.of(student));
        assertEquals(student, userService.findUserByName("Karine"));
    }
}
