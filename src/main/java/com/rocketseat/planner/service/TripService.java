package com.rocketseat.planner.service;

import com.rocketseat.planner.model.dto.TripRequestPayload;
import com.rocketseat.planner.model.entity.Trip;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface TripService {
    
    public UUID save(TripRequestPayload requestPayload);
    public ResponseEntity<Trip> getTripDetails(UUID id);
}
