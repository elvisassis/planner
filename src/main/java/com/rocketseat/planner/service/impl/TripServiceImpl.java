package com.rocketseat.planner.service.impl;

import com.rocketseat.planner.dto.TripRequestPayload;
import com.rocketseat.planner.model.entity.Trip;
import com.rocketseat.planner.repository.TripRepository;
import com.rocketseat.planner.service.ParticipantService;
import com.rocketseat.planner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    private final ParticipantService participantService;

    public UUID save(TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);
        this.tripRepository.save(newTrip);
        return newTrip.getId();
    }


    public ResponseEntity<Trip> getTripDetails(UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Trip> updateTrip(UUID id, TripRequestPayload payload) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip rowTrip = trip.get();
            rowTrip.setDestination(payload.destination());
            rowTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            rowTrip.setStartsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            this.tripRepository.save(rowTrip);
            return ResponseEntity.ok(rowTrip);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Trip> confirmTrip(UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);
            this.tripRepository.save(rawTrip);
            this.participantService.triggerConfirmationEmailToParticipants(id);
            return ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();
    }
}
