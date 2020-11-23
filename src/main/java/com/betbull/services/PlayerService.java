package com.betbull.services;

import com.betbull.dto.PlayerDto;
import com.betbull.dto.TeamDto;
import com.betbull.models.Player;
import com.betbull.models.Team;
import com.betbull.persistence.PlayerRepository;
import com.betbull.persistence.TeamRepository;
import com.google.common.collect.Streams;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .map(player -> playerMapper.toDto(player, player.getTeam()))
                .collect(toList());
    }

    public PlayerDto getPlayer(long id) {
        return playerRepository.findById(id)
                .map(player -> playerMapper.toDto(player, player.getTeam()))
                .orElse(null);
    }

    public PlayerDto save(String name, long teamId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isPresent()) {
            Player player = new Player(name, teamOptional.get());
            return playerMapper.toDto(playerRepository.save(player), teamOptional.get());
        }
        return null;
    }

    public PlayerDto updatePlayer(long id, String name) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Team team = playerOptional.get().getTeam();
            return playerMapper.toDto(playerRepository.save(new Player(id, name, team)), team);
        }
        return null;
    }

    public void deleteById(long id) {
        playerRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return playerRepository.existsById(id);
    }
}
