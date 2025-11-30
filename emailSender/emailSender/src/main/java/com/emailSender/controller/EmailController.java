package com.emailSender.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.emailSender.dto.EmailRequest;
import com.emailSender.service.EmailService;
import com.emailSender.service.ExcelReader;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ExcelReader excelReader;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmails(
            @RequestPart("payload") EmailRequest payload,
            @RequestPart("excel") MultipartFile excelFile,
            @RequestPart("resume") MultipartFile resumeFile) {

        try {
            JavaMailSender sender =
                    emailService.getCustomSender(payload.getUserEmail(), payload.getUserPassword());

            List<String> companyEmails = excelReader.readCompanyEmails(excelFile.getInputStream());

            for (String companyEmail : companyEmails) {
                emailService.sendEmail(
                        sender,
                        companyEmail,
                        payload.getSubject(),
                        payload.getBody(),
                        resumeFile
                );
            }

            return ResponseEntity.ok("All emails sent successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
