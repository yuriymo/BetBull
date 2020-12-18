package com.betbull.service;

import com.betbull.model.Player;
import com.betbull.model.PlayerDto;
import com.betbull.model.Team;
import com.betbull.persistence.PlayerRepository;
import com.betbull.persistence.TeamRepository;
import com.google.common.collect.Streams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        if (teamOptional.isEmpty()) {
            log.error("team id {} not exist", playerDto.getTeamId());
            return null;
        }
        return Optional.of(playerMapper.fromDto(playerDto, teamOptional.get()))
                .map(playerRepository::save)
                .map(playerMapper::toDto)
                .orElse(null);
    }

    public List<PlayerDto> save(List<PlayerDto> playerDtoList) {
        return playerDtoList.stream()
                .map(playerDto -> {
                    Optional<Team> teamOptional = teamRepository.findById(playerDto.getTeamId());
                    if (teamOptional.isEmpty()) {
                        log.error("team id {} not exist", playerDto.getTeamId());
                        return null;
                    }
                    return playerMapper.fromDtoIgnoreId(playerDto, teamOptional.get());
                })
                .filter(Objects::nonNull)
                .map(playerRepository::save)
                .map(playerMapper::toDto)
                .collect(toList());
    }

    public PlayerDto updatePlayer(long id, PlayerDto playerDto) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isEmpty()) {
            log.error("player id {} not exist", id);
            return null;
        }
        Player player = playerOptional.get();
        Optional.ofNullable(playerDto.getName()).ifPresent(player::setName);
        Optional.ofNullable(playerDto.getAge()).ifPresent(player::setAge);
        Optional.ofNullable(playerDto.getExperience()).ifPresent(player::setExperience);
        if (nonNull(playerDto.getTeamId()) && !playerDto.getTeamId().equals(player.getTeam().getId())) {
            Optional<Team> teamOptional = teamRepository.findById(playerDto.getTeamId());
            if (teamOptional.isEmpty()) {
                log.error("team id {} not exist", playerDto.getTeamId());
                return null;
            }
            player.setTeam(teamOptional.get());
        }
        return playerMapper.toDto(playerRepository.save(player));
    }

    public void deleteById(long id) {
        playerRepository.deleteById(id);
    }

    public boolean isNotExistById(long id) {
        return !playerRepository.existsById(id);
    }

    public double getPlayerTransferFees(long id) {
        PlayerDto playerDto = getPlayer(id);
        return (double) (playerDto.getExperience() * 100000 / playerDto.getAge()) * 1.1;
    }
}
