package com.budgetbuddy.service;

import com.budgetbuddy.shared.dto.UserDto;

public interface EmailService {

    void sendEmailVerificationEmail(UserDto userDto, String appBaseUrl);

    void sendPasswordResetEmail(UserDto userDto, String token);
    void sendEmail(String recipientAddress, String subject, String body);
}
