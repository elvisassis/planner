package com.rocketseat.planner.service;

import com.rocketseat.planner.dto.LinkCreateResponse;
import com.rocketseat.planner.dto.LinkData;
import com.rocketseat.planner.dto.LinkRequestPayload;
import com.rocketseat.planner.model.entity.Trip;

import java.util.List;
import java.util.UUID;

public interface LinkService {

    LinkCreateResponse createLink(Trip trip, LinkRequestPayload payload);
    List<LinkData> getLinksByTripId(UUID id);
}
