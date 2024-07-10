package com.rocketseat.planner.repository;

import com.rocketseat.planner.model.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {
    List<Link> getByTripId(UUID id);
}
