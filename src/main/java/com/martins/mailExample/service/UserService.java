package com.martins.mailExample.service;



import com.martins.mailExample.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// UserService.java
@Service
public interface UserService {
//    boolean existsByUsername(String username);
//    boolean existsByEmail(String email);
//    void save(User user);

    ResponseEntity<String> registerUser(User user);
    ResponseEntity<String> verifyEmail(String token);
    ResponseEntity<String> resendVerificationEmail(String email);
}