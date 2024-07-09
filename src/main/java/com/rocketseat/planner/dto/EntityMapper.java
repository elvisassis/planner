package com.rocketseat.planner.dto;

import com.rocketseat.planner.model.entity.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EntityMapper {
    public EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    Trip tripToEntity(TripRequestPayload payload);
}
