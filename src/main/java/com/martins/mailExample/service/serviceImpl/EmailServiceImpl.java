package com.martins.mailExample.service.serviceImpl;

import com.martins.mailExample.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String to, String token) {
        String subject = "Verify Your Email";
        String confirmationUrl = "http://localhost:8080/verify-email?token=" + token;
        String message = "Please verify your email by clicking the link: " + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
        logger.info("Verification email sent to: {}", to);
    }
}