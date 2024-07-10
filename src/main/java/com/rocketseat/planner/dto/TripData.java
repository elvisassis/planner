package com.rocketseat.planner.dto;

import com.rocketseat.planner.model.entity.Trip;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripData {

    public TripData(Trip trip) {
        this.id = trip.getId();
        this.destination = trip.getDestination();
        this.starts_at = trip.getStartsAt();
        this.ends_at = trip.getEndsAt();
        this.is_confirmed = trip.getIsConfirmed();
        this.owner_email = trip.getOwnerEmail();
        this.owner_name = trip.getOwnerName();
    }

    private UUID id;
    private String destination;
    private LocalDateTime starts_at;
    private LocalDateTime ends_at;
    private Boolean is_confirmed;
    private String owner_name;
    private String owner_email;
}
