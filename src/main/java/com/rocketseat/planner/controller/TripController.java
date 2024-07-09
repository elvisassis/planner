package com.rocketseat.planner.controller;

import com.rocketseat.planner.model.dto.TripCreateResponse;
import com.rocketseat.planner.model.dto.TripRequestPayload;
import com.rocketseat.planner.model.entity.Trip;
import com.rocketseat.planner.service.ParticipantService;
import com.rocketseat.planner.repository.TripRepository;
import com.rocketseat.planner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final ParticipantService participantService;

    private final TripService tripService;

    @PostMapping("/create")
    public ResponseEntity<TripCreateResponse> createTrip (@RequestBody TripRequestPayload payload) {

        UUID tripId = this.tripService.save(payload);
        this.participantService.registerParticipantEvent(payload.emails_to_invite(), tripId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TripCreateResponse(tripId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        return tripService.getTripDetails(id);
    }
}
