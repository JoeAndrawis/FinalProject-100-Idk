package com.example.UserMS.Service;

import com.example.UserMS.Factorypt.UserFactory;
import com.example.UserMS.Factorypt.UserInterface;
import com.example.UserMS.Repository.AdminRepository;
import com.example.UserMS.Repository.InstructorRepository;
import com.example.UserMS.Repository.StudentRepository;
import com.example.UserMS.SessionManager;
import com.example.UserMS.Models.AdminsEntity;
import com.example.UserMS.Models.InstructorsEntity;
import com.example.UserMS.Models.StudentsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public Object signUp(Object entity, String role) {
        switch (role.toLowerCase()) {
            case "student" -> {
                StudentsEntity input = (StudentsEntity) entity;
                UserInterface user = UserFactory.createUser("student", input.getName(), input.getEmail(), input.getPassword(), input.getGpa());
                return studentRepository.save(input);
            }
            case "admin" -> {
                AdminsEntity input = (AdminsEntity) entity;
                UserInterface user = UserFactory.createUser("admin", input.getName(), input.getEmail(), input.getPassword());
                return adminRepository.save(input);
            }
            case "instructor" -> {
                InstructorsEntity input = (InstructorsEntity) entity;
                UserInterface user = UserFactory.createUser("instructor", input.getName(), input.getEmail(), input.getPassword());
                return instructorRepository.save(input);
            }
            default -> throw new IllegalArgumentException("Invalid role");
        }
    }

    @Override
    public String login(String email) {
        Optional<? extends Object> user = studentRepository.findByEmail(email);
        if (user.isEmpty()) user = adminRepository.findByEmail(email);
        if (user.isEmpty()) user = instructorRepository.findByEmail(email);

        if (user.isPresent()) {
            SessionManager.getInstance().setToken("fake-token-for-" + email);
            return "Logged in. Token: " + SessionManager.getInstance().getToken();
        }
        return "User not found";
    }

    @Override
    public String logout() {
        SessionManager.getInstance().setToken(null);
        return "Logged out";
    }

    @Override
    public Object viewProfile(String email) {
        return findUserByEmail(email);
    }

    @Override
    public Object updateProfile(Object entity, String role) {
        if (role.equalsIgnoreCase("student") && entity instanceof StudentsEntity student) {
            Optional<StudentsEntity> existing = studentRepository.findByEmail(student.getEmail());
            if (existing.isPresent()) {
                StudentsEntity toUpdate = existing.get();
                toUpdate.setName(student.getName());
                toUpdate.setPassword(student.getPassword());
                toUpdate.setGpa(student.getGpa());
                return studentRepository.save(toUpdate);
            }
        } else if (role.equalsIgnoreCase("admin") && entity instanceof AdminsEntity admin) {
            Optional<AdminsEntity> existing = adminRepository.findByEmail(admin.getEmail());
            if (existing.isPresent()) {
                AdminsEntity toUpdate = existing.get();
                toUpdate.setName(admin.getName());
                toUpdate.setPassword(admin.getPassword());
                return adminRepository.save(toUpdate);
            }
        } else if (role.equalsIgnoreCase("instructor") && entity instanceof InstructorsEntity instructor) {
            Optional<InstructorsEntity> existing = instructorRepository.findByEmail(instructor.getEmail());
            if (existing.isPresent()) {
                InstructorsEntity toUpdate = existing.get();
                toUpdate.setName(instructor.getName());
                toUpdate.setPassword(instructor.getPassword());
                return instructorRepository.save(toUpdate);
            }
        }
        return null; // user not found
    }

    @Override
    public void deleteUser(String email) {
        studentRepository.findByEmail(email).ifPresent(studentRepository::delete);
        adminRepository.findByEmail(email).ifPresent(adminRepository::delete);
        instructorRepository.findByEmail(email).ifPresent(instructorRepository::delete);
    }

    @Override
    public Object findUserByEmail(String email) {
        Optional<StudentsEntity> student = studentRepository.findByEmail(email);
        if (student.isPresent()) return student.get();

        Optional<AdminsEntity> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) return admin.get();

        Optional<InstructorsEntity> instructor = instructorRepository.findByEmail(email);
        if (instructor.isPresent()) return instructor.get();

        return null;
    }

    @Override
    public Object findUserByName(String name) {
        Optional<StudentsEntity> student = studentRepository.findByName(name);
        if (student.isPresent()) return student.get();

        Optional<AdminsEntity> admin = adminRepository.findByName(name);
        if (admin.isPresent()) return admin.get();

        Optional<InstructorsEntity> instructor = instructorRepository.findByName(name);
        if (instructor.isPresent()) return instructor.get();

        return null;
    }

    @Override
    public Object verifyUser(String email) {
        Optional<StudentsEntity> student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            return "Student exists: " + student.get().getName();
        }

        Optional<AdminsEntity> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return "Admin exists: " + admin.get().getName();
        }

        Optional<InstructorsEntity> instructor = instructorRepository.findByEmail(email);
        if (instructor.isPresent()) {
            return "Instructor exists: " + instructor.get().getName();
        }

        return "User not found!";
    }

    @Override
    public Object findUserById(Long id) {
        Optional<StudentsEntity> student = studentRepository.findById(id);
        if (student.isPresent()) return student.get();

        Optional<AdminsEntity> admin = adminRepository.findById(id);
        if (admin.isPresent()) return admin.get();

        Optional<InstructorsEntity> instructor = instructorRepository.findById(id);
        if (instructor.isPresent()) return instructor.get();

        return null;
    }

    @Override
    public Object findStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Object findInstructorById(Long id) {
        return instructorRepository.findById(id).orElse(null);
    }
}