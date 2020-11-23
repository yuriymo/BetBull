package com.betbull.services;

import com.betbull.dto.PlayerDto;
import com.betbull.models.Player;
import com.betbull.models.Team;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-11-23T22:37:59+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
@Component
public class PlayerMapperImpl implements PlayerMapper {

    @Override
    public PlayerDto toDto(Player player, Team team) {
        if ( player == null && team == null ) {
            return null;
        }

        PlayerDto playerDto = new PlayerDto();

        if ( player != null ) {
            playerDto.setId( player.getId() );
            playerDto.setName( player.getName() );
        }
        if ( team != null ) {
            playerDto.setTeamId( team.getId() );
            playerDto.setTeamName( team.getName() );
        }

        return playerDto;
    }
}
