package com.rocketseat.planner.model.entity;

import com.rocketseat.planner.dto.ActivityRequestPayload;
import com.rocketseat.planner.dto.LinkRequestPayload;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", nullable = false)
    @NotNull
    private String title;

    @Column(name = "url", nullable = false)
    @NotNull
    private String url;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    public Link(Trip trip, LinkRequestPayload payload) {
        this.title = payload.title();
        this.url = payload.url();
        this.trip = trip;
    }
}
