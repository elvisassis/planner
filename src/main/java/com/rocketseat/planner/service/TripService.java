package com.rocketseat.planner.service;

import com.rocketseat.planner.dto.TripRequestPayload;
import com.rocketseat.planner.model.entity.Trip;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface TripService {
    
    UUID save(TripRequestPayload requestPayload);
    ResponseEntity<Trip> getTripDetails(UUID id);
    ResponseEntity<Trip> updateTrip(UUID id, TripRequestPayload payload);
    ResponseEntity<Trip> confirmTrip(UUID id);
}
