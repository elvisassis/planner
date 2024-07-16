package com.rocketseat.planner.controller;

import com.rocketseat.planner.dto.Email;
import com.rocketseat.planner.dto.TripRequestPayload;
import com.rocketseat.planner.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public void sendEmail(@RequestBody TripRequestPayload trip) throws MessagingException, IOException {
        //emailService.sendEmailConfirGuest(trip);
    }
}
