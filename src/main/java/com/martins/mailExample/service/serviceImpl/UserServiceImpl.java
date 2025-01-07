package com.martins.mailExample.service.serviceImpl;


import com.martins.mailExample.models.User;
import com.martins.mailExample.repository.UserRepository;
import com.martins.mailExample.service.EmailService;
import com.martins.mailExample.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

// UserServiceImpl.java
@Component
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


//    @Override
//    public boolean existsByUsername(String username) {
//        return userRepository.existsByUsername(username);
//    }
//
//    @Override
//    public boolean existsByEmail(String email) {
//        return userRepository.existsByEmail(email);
//    }
//
//    @Override
//    public void save(User user) {
//
//    }


    @Override
    public ResponseEntity<String> registerUser(User user) {
        LocalDateTime now = LocalDateTime.now();

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            logger.warn("Registration attempt for existing email: {}", user.getEmail());
            return ResponseEntity.badRequest().body("Email already registered.");
        }

        // Check for existing username
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            logger.warn("Registration attempt for existing username: {}", user.getUsername());
            return ResponseEntity.badRequest().body("Username already taken.");
        }

        String token = generateVerificationToken(user, now);
        userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), token);
        logger.info("Registration successful for email: {}", user.getEmail());
        return ResponseEntity.ok("Registration successful! Please check your email to verify.");
    }

    @Override
    public ResponseEntity<String> verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            logger.warn("Invalid verification token: {}", token);
            return ResponseEntity.badRequest().body("Invalid or expired verification token.");
        }

        if (user.getTokenCreatedAt().plusHours(24).isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Verification token has expired.");
        }

        user.setVerified(true);
        clearVerificationToken(user);
        userRepository.save(user);
        logger.info("Email verified successfully for: {}", user.getEmail());
        return ResponseEntity.ok("Email verified successfully!");
    }

    @Override
    public ResponseEntity<String> resendVerificationEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            logger.warn("Resend request for non-existent email: {}", email);
            return ResponseEntity.badRequest().body("Email not found.");
        }

        User user = optionalUser.get();
        if (user.isVerified()) {
            logger.warn("Resend request for already verified email: {}", email);
            return ResponseEntity.badRequest().body("Email is already verified.");
        }

        String token = generateVerificationToken(user, LocalDateTime.now());
        userRepository.save(user);
        emailService.sendVerificationEmail(email, token);
        logger.info("Resend verification email sent to: {}", email);
        return ResponseEntity.ok("Verification email resent. Please check your email.");
    }

    private String generateVerificationToken(User user, LocalDateTime now) {
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setTokenCreatedAt(now);
        return token;
    }

    private void clearVerificationToken(User user) {
        user.setVerificationToken(null);
        user.setTokenCreatedAt(null);
    }


//    @Override
//    public ResponseEntity<String> registerUser(User user) {
//        try {
//            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//                logger.warn("Registration attempt for existing email: {}", user.getEmail());
//                return ResponseEntity.badRequest().body("Email already registered.");
//            }
//
//            String token = UUID.randomUUID().toString();
//            user.setVerificationToken(token);
//            user.setTokenCreatedAt(LocalDateTime.now());
//            userRepository.save(user);
//            emailService.sendVerificationEmail(user.getEmail(), token);
//            logger.info("Registration successful for email: {}", user.getEmail());
//            return ResponseEntity.ok("Registration successful! Please check your email to verify.");
//        } catch (Exception e) {
//            logger.error("Error during registration:", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
//        }
//    }
//
//    @Override
//    public ResponseEntity<String> verifyEmail(String token) {
//        try {
//            User user = userRepository.findByVerificationToken(token);
//            if (user != null) {
//                if (user.getTokenCreatedAt().plusHours(24).isBefore(LocalDateTime.now())) {
//                    return ResponseEntity.badRequest().body("Verification token has expired.");
//                }
//                user.setVerified(true);
//                user.setVerificationToken(null);
//                user.setTokenCreatedAt(null);
//                userRepository.save(user);
//                logger.info("Email verified successfully for: {}", user.getEmail());
//                return ResponseEntity.ok("Email verified successfully!");
//            } else {
//                logger.warn("Invalid verification token: {}", token);
//                return ResponseEntity.badRequest().body("Invalid or expired verification token.");
//            }
//        } catch (Exception e) {
//            logger.error("Error during email verification:", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during email verification.");
//        }
//    }
//
//    @Override
//    public ResponseEntity<String> resendVerificationEmail(String email) {
//        try {
//            User user = userRepository.findByEmail(email).get();
//
//            if (user.isVerified()) {
//                logger.warn("Resend request for already verified email: {}", email);
//                return ResponseEntity.badRequest().body("Email is already verified.");
//            }
//
//            String token = UUID.randomUUID().toString();
//            user.setVerificationToken(token);
//            user.setTokenCreatedAt(LocalDateTime.now());
//            user.setVerificationRequestCount(user.getVerificationRequestCount() + 1);
//            userRepository.save(user);
//
//            emailService.sendVerificationEmail(email, token);
//            logger.info("Resend verification email sent to: {}", email);
//            return ResponseEntity.ok("Verification email resent. Please check your email.");
//        } catch (Exception e) {
//            logger.error("Error during resending verification email:", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while resending the verification email.");
//        }
//    }
}


