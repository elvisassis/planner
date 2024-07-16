package com.rocketseat.planner.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record Activity(UUID id, LocalDateTime occurs_at, String title) {
}
