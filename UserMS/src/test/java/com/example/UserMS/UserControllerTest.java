package com.example.UserMS;

import com.example.UserMS.Controller.UserController;
import com.example.UserMS.Models.AdminsEntity;
import com.example.UserMS.Models.InstructorsEntity;
import com.example.UserMS.Models.StudentsEntity;
import com.example.UserMS.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // signUpStudent
    @Test
    void testSignUpStudent_Success() {
        StudentsEntity student = new StudentsEntity("pass", "stud@guc.edu.eg", "Student");
        when(userService.signUp(student, "student")).thenReturn("Student signed up");
        assertEquals("Student signed up", userController.signUpStudent(student).getBody());
    }

    @Test
    void testSignUpStudent_Null() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userController.signUpStudent(null);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Student data cannot be null"));
    }

    @Test
    void testSignUpStudent_InvalidEmail() {
        StudentsEntity student = new StudentsEntity("pass", "", "Student");
        when(userService.signUp(student, "student")).thenReturn("Invalid email");
        assertEquals("Invalid email", userController.signUpStudent(student).getBody());
    }

    // signUpAdmin
    @Test
    void testSignUpAdmin_Success() {
        AdminsEntity admin = new AdminsEntity("pass", "admin@guc.edu.eg", "Admin");
        when(userService.signUp(admin, "admin")).thenReturn("Admin signed up");
        assertEquals("Admin signed up", userController.signUpAdmin(admin).getBody());
    }


    @Test
    void testSignUpAdmin_Null() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userController.signUpAdmin(null);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Admin data cannot be null"));
    }

    @Test
    void testSignUpAdmin_DuplicateEmail() {
        AdminsEntity admin = new AdminsEntity("pass", "admin@guc.edu.eg", "Admin");
        when(userService.signUp(admin, "admin")).thenReturn("Email already exists");
        assertEquals("Email already exists", userController.signUpAdmin(admin).getBody());
    }

    // signUpInstructor
    @Test
    void testSignUpInstructor_Success() {
        InstructorsEntity instructor = new InstructorsEntity("pass", "inst@guc.edu.eg", "Inst");
        when(userService.signUp(instructor, "instructor")).thenReturn("Instructor signed up");
        assertEquals("Instructor signed up", userController.signUpInstructor(instructor).getBody());
    }


    @Test
    void testSignUpInstructor_Null() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userController.signUpInstructor(null);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Instructor data cannot be null"));
    }

    @Test
    void testSignUpInstructor_Invalid() {
        InstructorsEntity instructor = new InstructorsEntity("", "", "");
        when(userService.signUp(instructor, "instructor")).thenReturn("Invalid data");
        assertEquals("Invalid data", userController.signUpInstructor(instructor).getBody());
    }

    // login
    @Test
    void testLogin_Success() {
        String email = "moamen@guc.edu.eg";
        when(userService.login(email)).thenReturn("Login successful");
        assertEquals("Login successful", userController.login(email).getBody());
    }

    @Test
    void testLogin_UserNotFound() {
        String email = "notfound@guc.edu.eg";
        when(userService.login(email)).thenThrow(new RuntimeException("User not found"));
        assertThrows(RuntimeException.class, () -> userController.login(email));
    }

    @Test
    void testLogin_EmptyEmail() {
        when(userService.login(""))
                .thenReturn("Missing email");
        assertEquals("Missing email", userController.login("").getBody());
    }

    // logout
    @Test
    void testLogout() {
        when(userService.logout()).thenReturn("Logged out successfully");
        assertEquals("Logged out successfully", userController.logout().getBody());
    }

    // deleteUser
    @Test
    void testDeleteUser_Success() {
        String email = "delete@guc.edu.eg";
        when(userService.deleteUser(email)).thenReturn(true);

        ResponseEntity<String> response = userController.deleteUser(email);
        assertEquals("User deleted", response.getBody());
    }

    // searchByEmail
    @Test
    void testSearchUserByEmail_Found() {
        String email = "test@guc.edu.eg";
        Object mockUser = new Object();
        when(userService.findUserByEmail(email)).thenReturn(mockUser);
        assertEquals(mockUser, userController.searchUserByEmail(email).getBody());
    }

    @Test
    void testSearchUserByEmail_NotFound() {
        when(userService.findUserByEmail("none@guc.edu.eg")).thenReturn(null);
        assertNull(userController.searchUserByEmail("none@guc.edu.eg").getBody());
    }

    // searchByName
    @Test
    void testSearchUserByName_Found() {
        String name = "Moamen";
        Object user = new Object();
        when(userService.findUserByName(name)).thenReturn(user);
        assertEquals(user, userController.searchUserByName(name).getBody());
    }

    // viewProfile
    @Test
    void testViewProfile() {
        String email = "moamen@guc.edu.eg";
        Object profile = new StudentsEntity();
        when(userService.viewProfile(email)).thenReturn(profile);
        assertEquals(profile, userController.viewProfile(email).getBody());
    }

    // editProfile
    @Test
    void testEditStudentProfile() {
        StudentsEntity student = new StudentsEntity("Moamen", "stud@guc.edu.eg", "pass", 3.5);
        when(userService.updateProfile(student, "student")).thenReturn("No changes made.");

        ResponseEntity<String> response = userController.editStudentProfile(student);
        assertEquals("Student profile updated:\nNo changes made.", response.getBody());
    }

    @Test
    void testEditAdminProfile() {
        AdminsEntity admin = new AdminsEntity("pass", "admin@guc.edu.eg", "OldName");
        when(userService.updateProfile(admin, "admin")).thenReturn("Name: OldName → NewName");

        ResponseEntity<String> response = userController.editAdminProfile(admin);
        assertEquals("Admin profile updated:\nName: OldName → NewName", response.getBody());
    }

    @Test
    void testEditInstructorProfile() {
        InstructorsEntity instructor = new InstructorsEntity("pass", "inst@guc.edu.eg", "Instructor");
        when(userService.updateProfile(instructor, "instructor")).thenReturn("Password: ******** → *********");

        ResponseEntity<String> response = userController.editInstructorProfile(instructor);
        assertEquals("Instructor profile updated:\nPassword: ******** → *********", response.getBody());
    }
    // verify
    @Test
    void testVerifyUser_Exist() {
        String email = "test@guc.edu.eg";
        when(userService.verifyUser(email)).thenReturn("Student exists: Test");
        assertEquals("Student exists: Test", userController.verifyUser(email).getBody());
    }


    @Test
    void testGetStudentById_Found() {
        Long id = 1L;
        StudentsEntity student = new StudentsEntity("Moamen", "moamen@guc.edu.eg", "123", 3.5);
        when(userService.findStudentById(id)).thenReturn(student);
        assertEquals(student, userController.getStudentById(id).getBody());
    }

    @Test
    void testGetStudentById_NotFound() {
        Long id = 999L;
        when(userService.findStudentById(id)).thenReturn(null);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userController.getStudentById(id);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }



    @Test
    void testGetInstructorById_Found() {
        Long id = 1L;
        InstructorsEntity instructor = new InstructorsEntity("Karine", "karine@guc.edu.eg", "abc123");
        when(userService.findInstructorById(id)).thenReturn(instructor);
        assertEquals(instructor, userController.getInstructorById(id).getBody());
    }

    @Test
    void testGetInstructorById_NotFound() {
        Long id = 2L;
        when(userService.findInstructorById(id)).thenReturn(null);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userController.getInstructorById(id);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}