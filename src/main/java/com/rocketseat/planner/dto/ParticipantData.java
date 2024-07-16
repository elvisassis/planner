package com.rocketseat.planner.dto;

import java.util.UUID;

public record ParticipantData(UUID id, String name, String email, Boolean is_confirmed) {
}
