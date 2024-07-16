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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        List<ActivityData> activityDataList = new ArrayList<>();

        if (trip.isPresent()) {

            List<LocalDate> tripDates = trip.get().getStartsAt().toLocalDate()
                    .datesUntil(trip.get().getEndsAt().toLocalDate().plus(1, ChronoUnit.DAYS)).toList();
            List<Activity> activities= activitiesRepository.findByTripId(tripId);

            if (!activities.isEmpty()) {
               tripDates.stream().forEach(tripDate -> {
                    List<com.rocketseat.planner.dto.Activity> activityList = activities.stream()
                            .filter(activity -> tripDate.isEqual(activity.getOccursAt().toLocalDate()))
                            .map(activity -> new com.rocketseat.planner.dto.Activity(activity.getId(), activity.getOccursAt(), activity.getTitle()))
                            .toList();
                    activityDataList.add(new ActivityData(tripDate.atTime(LocalTime.now()), activityList));
                    });
            }

        }
        return activityDataList;
    }
}
