package com.rocketseat.planner.dto;

import com.rocketseat.planner.model.entity.Trip;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record TripRequestPayload(String destination, String starts_at, @NotNull String ends_at, List<String> emails_to_invite, String owner_email, String owner_name) {

    public Trip toEntity(){
        return EntityMapper.INSTANCE.tripToEntity(this);
    }
}
