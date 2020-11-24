package com.betbull.service;

import com.betbull.models.Player;
import com.betbull.models.PlayerDto;
import com.betbull.models.Team;
import com.betbull.persistence.PlayerRepository;
import com.betbull.persistence.TeamRepository;
import com.google.common.collect.Streams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class PlayerService {

    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    public List<PlayerDto> getAll() {
        return Streams.stream(playerRepository.findAll())
                .map(playerMapper::toDto)
                .collect(toList());
    }

    public PlayerDto getPlayer(long id) {
        return playerRepository.findById(id)
                .map(playerMapper::toDto)
                .orElse(null);
    }

    public PlayerDto save(PlayerDto playerDto) {
        Optional<Team> teamOptional = teamRepository.findById(playerDto.getTeamId());
        if (teamOptional.isPresent()) {
            Player player = playerMapper.fromDto(playerDto, teamOptional.get());
            return playerMapper.toDto(playerRepository.save(player));
        } else {
            log.error("team id {} not exist", playerDto.getTeamId());
            return null;
        }
    }

    public PlayerDto updatePlayer(long id, PlayerDto playerDto) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            Optional.ofNullable(playerDto.getName()).ifPresent(player::setName);
            Optional.ofNullable(playerDto.getAge()).ifPresent(player::setAge);
            Optional.ofNullable(playerDto.getExperience()).ifPresent(player::setExperience);
            if (nonNull(playerDto.getTeamId()) && !playerDto.getTeamId().equals(player.getTeam().getId())) {
                Optional<Team> teamOptional = teamRepository.findById(playerDto.getTeamId());
                if (teamOptional.isPresent()) {
                    player.setTeam(teamOptional.get());
                    return playerMapper.toDto(playerRepository.save(player));
                } else {
                    log.error("team id {} not exist", playerDto.getTeamId());
                    return null;
                }
            } else {
                return playerMapper.toDto(playerRepository.save(player));
            }
        } else {
            log.error("player id {} not exist", id);
            return null;
        }
    }

    public void deleteById(long id) {
        playerRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return playerRepository.existsById(id);
    }
}
