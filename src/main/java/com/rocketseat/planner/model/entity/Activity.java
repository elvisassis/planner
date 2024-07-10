package com.rocketseat.planner.model.entity;

import com.rocketseat.planner.dto.ActivityRequestPayload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "activities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "occurs_at", nullable = false)
    private LocalDateTime occursAt;

    @Column(name = "titulo", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public Activity(Trip trip, ActivityRequestPayload payload) {
        this.occursAt = LocalDateTime.parse(payload.occurs_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.title = payload.title();
        this.trip = trip;
    }
}
