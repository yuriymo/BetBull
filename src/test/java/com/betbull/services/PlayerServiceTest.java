package com.betbull.services;

import com.betbull.Application;
import com.betbull.dto.PlayerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@SpringBootTest(classes = Application.class)
public class PlayerServiceTest {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;

    @Test
    void addPlayer() {
        long team1 = teamService.save("team-1").getId();
        long team2 = teamService.save("team-2").getId();
        long player1 = playerService.save("player-1", team1).getId();
        long player2 = playerService.save("player-2", team2).getId();
        Set<Long> playerSet = Set.of(player1, player2);
        Set<Long> players = playerService.getAll().stream()
                .map(PlayerDto::getId)
                .filter(playerSet::contains)
                .collect(toSet());
        if (!players.equals(playerSet)) {
            Assertions.fail("addPlayer");
        }
    }

    @Test
    void updatePlayer() {
        long team1 = teamService.save("team-1").getId();
        long player1 = playerService.save("player-1", team1).getId();
        playerService.updatePlayer(player1, "update test");
        if (!playerService.getPlayer(player1).getName().equals("update test")) {
            Assertions.fail("updatePlayer");
        }
    }

    @Test
    void deletePlayer() {
        long team1 = teamService.save("team-1").getId();
        Long player1 = playerService.save("player-1", team1).getId();
        playerService.deleteById(player1);
        Set<Long> players = playerService.getAll().stream()
                .map(PlayerDto::getId)
                .filter(player1::equals)
                .collect(toSet());
        if (!players.isEmpty()) {
            Assertions.fail("updatePlayer");
        }
    }
}