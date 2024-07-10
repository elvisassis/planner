package com.rocketseat.planner.service;

import com.rocketseat.planner.dto.ParticipantRequestPayload;
import com.rocketseat.planner.model.entity.Participant;
import com.rocketseat.planner.model.entity.Trip;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {


    ResponseEntity<List<Participant>> getAllParticipants(UUID tripId);
    ResponseEntity<Participant> getParticipantDetail(UUID id);
    ResponseEntity<Participant> confirmParticipants(UUID id, ParticipantRequestPayload payload);
    Participant registerParticipantToEvent(String participantToInvite, Trip trip);
    void registerParticipantToEvent(List<String> participantToInvite, Trip trip);
    void triggerConfirmationEmailToParticipants(UUID tripId);
    void triggerConfirmationEmailToParticipant(String email);


}
