package com.emailSender.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String userEmail;
    private String userPassword;
    private String subject;
    private String body;
}
