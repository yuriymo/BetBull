package com.betbull.service;

import com.betbull.Application;
import com.betbull.models.PlayerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        long player1 = playerService.save(new PlayerDto(null, "player-1", 33, 13, team1, null)).getId();
        long player2 = playerService.save(new PlayerDto(null, "player-2", 33, 13, team2, null)).getId();
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
    void updatePlayerName() {
        long team1 = teamService.save("team-1").getId();
        long player1 = playerService.save(new PlayerDto(null, "player-1", 33, 13, team1, null)).getId();
        playerService.updatePlayer(player1, new PlayerDto( null, "update test", 33, 13, team1, null));
        if (!playerService.getPlayer(player1).getName().equals("update test")) {
            Assertions.fail("updatePlayer");
        }
    }

    @Test
    void updatePlayerTeam() {
        long team1 = teamService.save("team-1").getId();
        long team2 = teamService.save("team-2").getId();
        long player1 = playerService.save(new PlayerDto(null, "player-1", 33, 13, team1, null)).getId();
        playerService.updatePlayer(player1, new PlayerDto( null, null, null, null, team2, null));
        if (!playerService.getPlayer(player1).getTeamId().equals(team2)) {
            Assertions.fail("updatePlayer");
        }
    }

    @Test
    void deletePlayer() {
        long team1 = teamService.save("team-1").getId();
        Long player1 = playerService.save(new PlayerDto(null, "player-1", 33, 13, team1, null)).getId();
        playerService.deleteById(player1);
        Set<Long> players = playerService.getAll().stream()
                .map(PlayerDto::getId)
                .filter(player1::equals)
                .collect(toSet());
        if (!players.isEmpty()) {
            Assertions.fail("updatePlayer");
        }
    }

    @Test
    void getPlayerTransferFees() {
        long team1 = teamService.save("team-1").getId();
        long player1 = playerService.save(new PlayerDto(null, "player-1", 10, 10, team1, null)).getId();
        double playerTransferFees = playerService.getPlayerTransferFees(player1);
        double controlFee = (double) (10 * 100000 / 10) * 1.1;
        if (playerTransferFees != controlFee) {
            Assertions.fail("updatePlayer");
        }
    }
}