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
        // Existing student in repo
        StudentsEntity existing = new StudentsEntity("OldName", "stud@guc.edu.eg", "oldpass", 2.0);
        // Updated student input
        StudentsEntity input = new StudentsEntity("NewName", "stud@guc.edu.eg", "newpass", 3.7);

        when(studentRepository.findByEmail("stud@guc.edu.eg")).thenReturn(Optional.of(existing));
        when(studentRepository.save(any(StudentsEntity.class))).thenAnswer(i -> i.getArgument(0));

        String result = userService.updateProfile(input, "student").toString();

        System.out.println("Update result:\n" + result);

        assertTrue(result.contains("Name: OldName → NewName"));
        assertTrue(result.contains("Password: ******** → *********"));
        assertTrue(result.contains("GPA: 2.0 → 3.7"));
    }

    @Test
    void testUpdateStudentProfile_NotFound() {
        StudentsEntity student = new StudentsEntity("pass", "none@guc.edu.eg", "Nada");
        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateProfile(student, "student");
        });

        assertEquals("Student with this email does not exist.", exception.getMessage());
    }

    // ---------- Admin Tests ----------
    @Test
    void testSignUpAdmin() {
        AdminsEntity admin = new AdminsEntity("1234", "admin@guc.edu.eg", "Admin");
        when(adminRepository.save(admin)).thenReturn(admin);
        assertEquals(admin, userService.signUp(admin, "admin"));
    }

    @Test
    void testUpdateAdminProfile_NotFound() {
        AdminsEntity admin = new AdminsEntity("4321", "ghost@guc.edu.eg", "Ghost");
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateProfile(admin, "admin");
        });

        assertEquals("Admin with this email does not exist.", exception.getMessage());
    }

    // ---------- Instructor Tests ----------
    @Test
    void testSignUpInstructor() {
        InstructorsEntity instructor = new InstructorsEntity("instpass", "inst@guc.edu.eg", "Instructor");
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        assertEquals(instructor, userService.signUp(instructor, "instructor"));
    }

    @Test
    void testUpdateInstructorProfile_NotFound() {
        InstructorsEntity instructor = new InstructorsEntity("pass", "missing@guc.edu.eg", "Ghost" );
        when(instructorRepository.findByEmail(instructor.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateProfile(instructor, "instructor");
        });

        assertEquals("Instructor with this email does not exist.", exception.getMessage());
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
