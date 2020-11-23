package com.betbull.services;

import com.betbull.dto.PlayerDto;
import com.betbull.models.Player;
import com.betbull.models.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    @Mapping(source = "player.id", target = "id")
    @Mapping(source = "player.name", target = "name")
    PlayerDto toDto(Player player, Team team);
}
