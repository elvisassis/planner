package com.rocketseat.planner.service;

import com.rocketseat.planner.dto.ActivityData;
import com.rocketseat.planner.dto.ActivityRequestPayload;
import com.rocketseat.planner.dto.ActivityResponse;
import com.rocketseat.planner.model.entity.Trip;

import java.util.List;
import java.util.UUID;

public interface ActivityService {

    ActivityResponse createActivities(Trip trip, ActivityRequestPayload payload);
    List<ActivityData> getActivitiesByTripId(UUID tripId);
}
