package com.betbull.service;

import com.betbull.model.Player;
import com.betbull.model.PlayerDto;
import com.betbull.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    @Mapping(source = "player.team.id", target = "teamId")
    @Mapping(source = "player.team.name", target = "teamName")
    @Mapping(source = "player.id", target = "id")
    @Mapping(source = "player.name", target = "name")
    @Mapping(source = "player.age", target = "age")
    @Mapping(source = "player.experience", target = "experience")
    PlayerDto toDto(Player player);

    @Mapping(source = "team", target = "team")
    @Mapping(source = "playerDto.id", target = "id")
    @Mapping(source = "playerDto.name", target = "name")
    @Mapping(source = "playerDto.age", target = "age")
    @Mapping(source = "playerDto.experience", target = "experience")
    Player fromDto(PlayerDto playerDto, Team team);

    @Mapping(source = "team", target = "team")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "playerDto.name", target = "name")
    @Mapping(source = "playerDto.age", target = "age")
    @Mapping(source = "playerDto.experience", target = "experience")
    Player fromDtoIgnoreId(PlayerDto playerDto, Team team);
}
