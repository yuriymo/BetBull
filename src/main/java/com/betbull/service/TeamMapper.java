package com.betbull.service;

import com.betbull.model.TeamDto;
import com.betbull.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamDto toDto(Team team);
    Team fromDto(TeamDto team);
    @Mapping(target = "id", ignore = true)
    Team fromDtoIgnoreId(TeamDto team);
}
