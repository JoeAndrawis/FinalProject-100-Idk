package client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserServiceClient {
    
    @GetMapping("/api/users/{userId}/exists")
    ResponseEntity<Boolean> checkUserExists(@PathVariable String userId);
    
    @GetMapping("/api/users/{userId}/is-instructor")
    ResponseEntity<Boolean> isUserInstructor(@PathVariable String userId);
    
    @GetMapping("/api/users/{userId}")
    ResponseEntity<UserDto> getUserById(@PathVariable String userId);
}