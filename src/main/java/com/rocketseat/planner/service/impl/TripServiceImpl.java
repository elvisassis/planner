package com.rocketseat.planner.service.impl;

import com.rocketseat.planner.dto.ParticipantCreateResponse;
import com.rocketseat.planner.dto.TripData;
import com.rocketseat.planner.dto.TripRequestPayload;
import com.rocketseat.planner.exceptionHandler.StartAtCannotBeBiggerEndsAtException;
import com.rocketseat.planner.model.entity.Participant;
import com.rocketseat.planner.model.entity.Trip;
import com.rocketseat.planner.repository.TripRepository;
import com.rocketseat.planner.service.EmailService;
import com.rocketseat.planner.service.ParticipantService;
import com.rocketseat.planner.service.TripService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    private final ParticipantService participantService;

    private final EmailService emailService;


    @Override
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(UUID id, String emailParticipant) {
        Optional<Trip> trip = tripRepository.findById(id);
        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            Participant participant = participantService.registerParticipantToEvent(emailParticipant, rawTrip);
            if (rawTrip.getIsConfirmed()) this.participantService.triggerConfirmationEmailToParticipant(rawTrip, emailParticipant);

            return ResponseEntity.ok(new ParticipantCreateResponse(participant.getId()));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public Trip createTrip(TripRequestPayload payload) {
        validateTrip(payload);
        Trip newTrip = new Trip(payload);
        triggerConfirmationEmailToTrip(newTrip);
        return this.tripRepository.save(newTrip);
    }

    @Override
    public ResponseEntity<TripData> getTripDetails(UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        return trip.map(t -> ResponseEntity.ok(new TripData(t))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Optional<Trip>findById(UUID id) {
        return tripRepository.findById(id);
    }

    @Override
    public ResponseEntity<TripData> updateTrip(UUID id, TripRequestPayload payload) {
        validateTrip(payload);
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setDestination(payload.destination());
            rawTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            this.tripRepository.save(rawTrip);
            return ResponseEntity.ok(new TripData(rawTrip));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public ResponseEntity<TripData> confirmTrip(UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);
            this.tripRepository.save(rawTrip);
            List<String> listEmailParticipants = this.participantService.findByTripId(id).stream().map((Participant::getEmail)).toList();
            this.participantService.triggerConfirmationEmailToParticipants(rawTrip, listEmailParticipants);

            return ResponseEntity.ok(new TripData(rawTrip));
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    public void triggerConfirmationEmailToTrip(Trip trip) {
        this.emailService.sendEmailConfirTrip(trip);
    }

    private void validateTrip(TripRequestPayload payload) {
        if(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME).isAfter(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME))) {
            throw new StartAtCannotBeBiggerEndsAtException();
        }
    }
}
