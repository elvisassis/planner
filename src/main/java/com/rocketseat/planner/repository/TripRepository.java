package com.rocketseat.planner.repository;

import com.rocketseat.planner.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.rmi.server.UID;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
}
