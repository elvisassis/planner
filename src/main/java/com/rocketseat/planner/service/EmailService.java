package com.rocketseat.planner.service;

import com.rocketseat.planner.dto.Email;
import com.rocketseat.planner.dto.TripRequestPayload;
import com.rocketseat.planner.model.entity.Trip;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface EmailService {
    void sendEmailConfirGuest(Trip trip, List<String> emails);
    void sendEmailConfirTrip(Trip trip);
}

