package com.rocketseat.planner.repository;

import com.rocketseat.planner.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.rmi.server.UID;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID> {
}
