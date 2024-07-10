package com.rocketseat.planner.service.impl;

import com.rocketseat.planner.dto.ActivityData;
import com.rocketseat.planner.dto.ActivityRequestPayload;
import com.rocketseat.planner.dto.ActivityResponse;
import com.rocketseat.planner.model.entity.Activity;
import com.rocketseat.planner.model.entity.Trip;
import com.rocketseat.planner.repository.ActivitiesRepository;
import com.rocketseat.planner.service.ActivityService;
import com.rocketseat.planner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivitiesRepository activitiesRepository;

    private final TripService tripService;

    @Override
    public ActivityResponse createActivities(Trip trip, ActivityRequestPayload payload) {

            Activity newActivity = new Activity(trip, payload);
            this.activitiesRepository.save(newActivity);

            return new ActivityResponse(newActivity.getId());
    }

    @Override
    public List<ActivityData> getActivitiesByTripId(UUID tripId) {
        Optional<Trip> trip = tripService.findById(tripId);
        if (trip.isPresent()) {
            List<Activity> activities = activitiesRepository.findByTripId(tripId);
            if (!activities.isEmpty()) {
                List<ActivityData> activityData = activities.stream().map( activity -> new ActivityData(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();

                return activityData;
            }
        }
        return List.of();
    }
}
