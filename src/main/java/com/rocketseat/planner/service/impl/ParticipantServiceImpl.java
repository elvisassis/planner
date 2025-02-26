package com.rocketseat.planner.service.impl;

import com.rocketseat.planner.dto.ParticipantData;
import com.rocketseat.planner.dto.ParticipantRequestPayload;
import com.rocketseat.planner.model.entity.Participant;
import com.rocketseat.planner.model.entity.Trip;
import com.rocketseat.planner.repository.ParticipantRepository;
import com.rocketseat.planner.service.EmailService;
import com.rocketseat.planner.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    private final EmailService emailService;

    @Override
    public List<ParticipantData> getAllParticipants(UUID tripId) {
        List<Participant> participants = this.participantRepository.findByTripId(tripId);
        List<ParticipantData> participantDataList = new ArrayList<>();
        if (!participants.isEmpty()) {
            participantDataList = participants.stream().map(participant ->
                    new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).collect(Collectors.toUnmodifiableList());
        }
        return participantDataList;
    }

    @Override
    public ResponseEntity<Participant> getParticipantDetail(UUID id) {
        Optional<Participant> participant = participantRepository.findById(id);
        return participant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Participant> confirmParticipant(UUID id, ParticipantRequestPayload payload) {
        Optional<Participant> participant = this.participantRepository.findById(id);
        if (participant.isPresent()){
            Participant rawParticipant =  participant.get();
            rawParticipant.setIsConfirmed(true);
            rawParticipant.setName(payload.name());
            this.participantRepository.save(rawParticipant);

            return ResponseEntity.ok(rawParticipant);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public Participant registerParticipantToEvent(String emailParticipantToInvite, Trip trip) {
        return this.participantRepository.save(new Participant(emailParticipantToInvite, trip));
    }

    public void registerParticipantsToEvent(List<String> participantToInvite, Trip trip) {
        List<Participant> participants = participantToInvite.stream().map(email -> new Participant(email, trip)).toList();
        this.participantRepository.saveAll(participants);
    }

    @Override
    public void triggerConfirmationEmailToParticipants(Trip trip, List<String> emails) {
        this.emailService.sendEmailConfirGuest(trip, emails);
    }

    @Override
    public void triggerConfirmationEmailToParticipant(Trip trip, String email) {
        this.emailService.sendEmailConfirGuest(trip, List.of(email));
    }

    @Override
    public List<Participant> findByTripId(UUID tripId){
        return this.participantRepository.findByTripId(tripId);
    }
}


