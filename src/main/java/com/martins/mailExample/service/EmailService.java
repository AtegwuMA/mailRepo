package com.martins.mailExample.service;

public interface EmailService {
    void sendVerificationEmail(String to, String token);
}
