package com.example.UserMS.Controller;

import com.example.UserMS.Service.UserService;
import com.example.UserMS.Models.AdminsEntity;
import com.example.UserMS.Models.InstructorsEntity;
import com.example.UserMS.Models.StudentsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup/student")
    public ResponseEntity<Object> signUpStudent(@RequestBody(required = false) StudentsEntity student) {
        if (student == null) throw new IllegalArgumentException("Student data cannot be null");
        return ResponseEntity.ok(userService.signUp(student, "student"));
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<Object> signUpAdmin(@RequestBody(required = false) AdminsEntity admin) {
        if (admin == null) throw new IllegalArgumentException("Admin data cannot be null");
        return ResponseEntity.ok(userService.signUp(admin, "admin"));
    }

    @PostMapping("/signup/instructor")
    public ResponseEntity<Object> signUpInstructor(@RequestBody(required = false) InstructorsEntity instructor) {
        if (instructor == null) throw new IllegalArgumentException("Instructor data cannot be null");
        return ResponseEntity.ok(userService.signUp(instructor, "instructor"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email) {
        return ResponseEntity.ok(userService.login(email));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        userService.logout();
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/searchByEmail")
    public ResponseEntity<Object> searchUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }
    @GetMapping("/searchByName")
    public ResponseEntity<Object> searchUserByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.findUserByName(name));
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<Object> viewProfile(@PathVariable String email) {
        return ResponseEntity.ok(userService.viewProfile(email));
    }

    @PutMapping("/editProfile/student")
    public ResponseEntity<String> editStudentProfile(@RequestBody StudentsEntity student) {
        userService.updateProfile(student, "student");
        return ResponseEntity.ok("Student profile updated successfully!");
    }

    @PutMapping("/editProfile/admin")
    public ResponseEntity<String> editAdminProfile(@RequestBody AdminsEntity admin) {
        userService.updateProfile(admin, "admin");
        return ResponseEntity.ok("Admin profile updated successfully!");
    }

    @PutMapping("/editProfile/instructor")
    public ResponseEntity<String> editInstructorProfile(@RequestBody InstructorsEntity instructor) {
        userService.updateProfile(instructor, "instructor");
        return ResponseEntity.ok("Instructor profile updated successfully!");
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok("User deleted");
    }
    @GetMapping("/verify")
    public ResponseEntity<Object> verifyUser(@RequestParam String email) {
        return ResponseEntity.ok(userService.verifyUser(email));
    }

}
