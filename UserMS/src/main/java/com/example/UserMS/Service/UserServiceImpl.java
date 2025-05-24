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
    public String login(String email, String password) {
        Optional<StudentsEntity> student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            if (student.get().getPassword().equals(password)) {
                SessionManager.getInstance().setToken("fake-token-for-" + email);
                return "Logged in. Token: " + SessionManager.getInstance().getToken();
            } else {
                return "Incorrect password";
            }
        }

        Optional<AdminsEntity> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            if (admin.get().getPassword().equals(password)) {
                SessionManager.getInstance().setToken("fake-token-for-" + email);
                return "Logged in. Token: " + SessionManager.getInstance().getToken();
            } else {
                return "Incorrect password";
            }
        }

        Optional<InstructorsEntity> instructor = instructorRepository.findByEmail(email);
        if (instructor.isPresent()) {
            if (instructor.get().getPassword().equals(password)) {
                SessionManager.getInstance().setToken("fake-token-for-" + email);
                return "Logged in. Token: " + SessionManager.getInstance().getToken();
            } else {
                return "Incorrect password";
            }
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
    public String updateProfile(Object entity, String role) {
        StringBuilder changes = new StringBuilder();

        if (role.equalsIgnoreCase("student") && entity instanceof StudentsEntity student) {
            Optional<StudentsEntity> existing = studentRepository.findByEmail(student.getEmail());
            if (existing.isEmpty()) throw new IllegalArgumentException("Student with this email does not exist.");

            StudentsEntity toUpdate = existing.get();

            if (!toUpdate.getName().equals(student.getName())) {
                changes.append("Name: ").append(toUpdate.getName()).append(" → ").append(student.getName()).append("\n");
                toUpdate.setName(student.getName());
            }

            if (!toUpdate.getPassword().equals(student.getPassword())) {
                changes.append("Password: ******** → *********\n");
                toUpdate.setPassword(student.getPassword());
            }

            if (Double.compare(toUpdate.getGpa(), student.getGpa()) != 0) {
                changes.append("GPA: ").append(toUpdate.getGpa()).append(" → ").append(student.getGpa()).append("\n");
                toUpdate.setGpa(student.getGpa());
            }

            studentRepository.save(toUpdate);
            return changes.length() > 0 ? changes.toString().trim() : "No changes made.";
        }

        else if (role.equalsIgnoreCase("admin") && entity instanceof AdminsEntity admin) {
            Optional<AdminsEntity> existing = adminRepository.findByEmail(admin.getEmail());
            if (existing.isEmpty()) throw new IllegalArgumentException("Admin with this email does not exist.");

            AdminsEntity toUpdate = existing.get();

            if (!toUpdate.getName().equals(admin.getName())) {
                changes.append("Name: ").append(toUpdate.getName()).append(" → ").append(admin.getName()).append("\n");
                toUpdate.setName(admin.getName());
            }

            if (!toUpdate.getPassword().equals(admin.getPassword())) {
                changes.append("Password: ******** → *********\n");
                toUpdate.setPassword(admin.getPassword());
            }

            adminRepository.save(toUpdate);
            return changes.length() > 0 ? changes.toString().trim() : "No changes made.";
        }

        else if (role.equalsIgnoreCase("instructor") && entity instanceof InstructorsEntity instructor) {
            Optional<InstructorsEntity> existing = instructorRepository.findByEmail(instructor.getEmail());
            if (existing.isEmpty()) throw new IllegalArgumentException("Instructor with this email does not exist.");

            InstructorsEntity toUpdate = existing.get();

            if (!toUpdate.getName().equals(instructor.getName())) {
                changes.append("Name: ").append(toUpdate.getName()).append(" → ").append(instructor.getName()).append("\n");
                toUpdate.setName(instructor.getName());
            }

            if (!toUpdate.getPassword().equals(instructor.getPassword())) {
                changes.append("Password: ******** → *********\n");
                toUpdate.setPassword(instructor.getPassword());
            }

            instructorRepository.save(toUpdate);
            return changes.length() > 0 ? changes.toString().trim() : "No changes made.";
        }

        throw new IllegalArgumentException("Invalid role or entity.");
    }

    @Override
    public boolean deleteUser(String email) {
        boolean deleted = false;

        Optional<StudentsEntity> student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            deleted = true;
        }

        Optional<AdminsEntity> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            adminRepository.delete(admin.get());
            deleted = true;
        }

        Optional<InstructorsEntity> instructor = instructorRepository.findByEmail(email);
        if (instructor.isPresent()) {
            instructorRepository.delete(instructor.get());
            deleted = true;
        }

        return deleted;
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