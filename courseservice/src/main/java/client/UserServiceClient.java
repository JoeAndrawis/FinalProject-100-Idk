package client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "userms", url = "http://userms:8080" )
    
    public interface UserServiceClient {
        @GetMapping("/users/instructor/{id}")  
        InstructorDto getInstructorDetails(@PathVariable Long id);
        @GetMapping("/users/student/{id}")  
        StudentDto getStudentDetails(@PathVariable Long id);
        
    }
