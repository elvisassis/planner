package com.rocketseat.planner.service.impl;

import com.rocketseat.planner.service.ParticipantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    public void registerParticipantEvent(List<String> participantToInvite, UUID tripId) {}
    public void triggerConfirmationEmailToParticipants (UUID tripId) {}
}


