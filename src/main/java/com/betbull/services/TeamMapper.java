package com.betbull.services;

import com.betbull.dto.TeamDto;
import com.betbull.models.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamDto toDto(Team team);
}
