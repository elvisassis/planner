package com.rocketseat.planner.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ActivityData(LocalDateTime date, List<Activity> activities) {
}
