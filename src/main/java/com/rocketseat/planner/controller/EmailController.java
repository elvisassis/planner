package com.rocketseat.planner.controller;

import com.rocketseat.planner.dto.Email;
import com.rocketseat.planner.dto.TripRequestPayload;
import com.rocketseat.planner.exceptionHandler.FieldRequiredNotFound;
import com.rocketseat.planner.model.entity.Trip;
import com.rocketseat.planner.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping()
    public String sendEmail(@RequestBody TripRequestPayload trip) throws MessagingException, IOException {
        Trip trip1 = new Trip(trip);
        if (trip.emails_to_invite() == null){
            throw new FieldRequiredNotFound("email");
        }
        emailService.sendEmailConfirGuest(trip1, trip.emails_to_invite());
        return "Email enviado com sucesso";
    }

    @PostMapping("/teste/{numero}")
    public String teste(@PathVariable Integer numero) {

        return "Teste de exception: " + numero;
    }
}
