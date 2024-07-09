package com.rocketseat.planner.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {

    public void registerParticipantEvent(List<String> participantToInvite, UUID tripId);
    public void triggerConfirmationEmailToParticipants (UUID tripId);


}
