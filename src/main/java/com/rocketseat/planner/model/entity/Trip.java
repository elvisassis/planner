package com.rocketseat.planner.model.entity;

import com.rocketseat.planner.dto.TripRequestPayload;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trips")
public class Trip {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id")
     private UUID id;

     @Column(name = "destination", nullable = false)
     @NotNull
     private String destination;

     @Column(name = "starts_at", nullable = false)
     @NotNull
     private LocalDateTime startsAt;

     @Column(name = "ends_at", nullable = false)
     @NotNull
     private LocalDateTime endsAt;

     @Column(name = "is_confirmed", nullable = false)
     @NotNull
     private Boolean isConfirmed;

     @Column(name = "owner_name", nullable = false)
     @NotNull
     private String ownerName;

     @Column(name = "owner_email")
     @NotNull
     private String ownerEmail;

     public Trip(TripRequestPayload data) {
          this.destination = data.destination();
          this.isConfirmed = false;
          this.ownerEmail = data.owner_email();
          this.ownerName = data.owner_name();
          this.startsAt = LocalDateTime.parse(data.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
          this.endsAt = LocalDateTime.parse(data.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
     }



}
