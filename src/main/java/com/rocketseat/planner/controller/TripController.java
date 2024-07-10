package com.rocketseat.planner.controller;

import com.rocketseat.planner.dto.*;
import com.rocketseat.planner.model.entity.Trip;
import com.rocketseat.planner.service.ActivityService;
import com.rocketseat.planner.service.ParticipantService;
import com.rocketseat.planner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final ParticipantService participantService;

    private final TripService tripService;

    private final ActivityService activityService;

    @PostMapping("/create")
    public ResponseEntity<TripCreateResponse> createTrip (@RequestBody TripRequestPayload payload) {

        Trip trip = this.tripService.createTrip(payload);
        this.participantService.registerParticipantToEvent(payload.emails_to_invite(), trip);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TripCreateResponse(trip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        return tripService.getTripDetails(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {
        return tripService.updateTrip(id, payload);
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirTrip(@PathVariable UUID id) {
        return tripService.confirmTrip(id);
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload participantRequestPayload) {
        return this.tripService.inviteParticipant(id, participantRequestPayload.email());
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id) {
        return this.participantService.getAllParticipants(id);
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> createActivities(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        Optional<Trip> trip = tripService.findById(id);
        if (trip.isPresent()) {
            ActivityResponse activityResponse = activityService.createActivities(trip.get(), payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(activityResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID id) {
        List<ActivityData> activityDataList = activityService.getActivitiesByTripId(id);

        return ResponseEntity.ok(activityDataList);
    }

}
