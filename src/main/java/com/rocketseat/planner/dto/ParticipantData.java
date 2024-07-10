package com.rocketseat.planner.dto;

import java.util.UUID;

public record ParticipantData(UUID id, String nome, String email, Boolean isConfirmed) {
}
