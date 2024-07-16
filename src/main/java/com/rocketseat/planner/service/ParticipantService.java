package com.rocketseat.planner.service;

import com.rocketseat.planner.dto.ParticipantData;
import com.rocketseat.planner.dto.ParticipantRequestPayload;
import com.rocketseat.planner.model.entity.Participant;
import com.rocketseat.planner.model.entity.Trip;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {


    List<ParticipantData>  getAllParticipants(UUID tripId);
    ResponseEntity<Participant> getParticipantDetail(UUID id);
    ResponseEntity<Participant> confirmParticipant(UUID id, ParticipantRequestPayload payload);
    Participant registerParticipantToEvent(String participantToInvite, Trip trip);
    void registerParticipantsToEvent(List<String> participantToInvite, Trip trip);
    void triggerConfirmationEmailToParticipants(Trip trip, List<String> emails);
    void triggerConfirmationEmailToParticipant(Trip trip, String email);
    List<Participant> findByTripId(UUID tripID);

}
