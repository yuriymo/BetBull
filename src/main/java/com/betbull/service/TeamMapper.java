package com.betbull.service;

import com.betbull.models.TeamDto;
import com.betbull.models.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamDto toDto(Team team);
}
