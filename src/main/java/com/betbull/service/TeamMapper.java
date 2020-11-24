package com.betbull.service;

import com.betbull.model.TeamDto;
import com.betbull.model.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamDto toDto(Team team);
}
