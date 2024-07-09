package com.rocketseat.planner.service.impl;

import com.rocketseat.planner.model.dto.TripRequestPayload;
import com.rocketseat.planner.model.entity.Trip;
import com.rocketseat.planner.repository.TripRepository;
import com.rocketseat.planner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    public UUID save(TripRequestPayload requestPayload) {
        Trip newTrip = new Trip(requestPayload);
        this.tripRepository.save(newTrip);
        return newTrip.getId();
    }


    public ResponseEntity<Trip> getTripDetails(UUID tripId) {
        Optional<Trip> trip = this.tripRepository.findById(tripId);
        trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
