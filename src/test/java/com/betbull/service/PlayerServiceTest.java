package com.betbull.service;

import com.betbull.Application;
import com.betbull.model.PlayerDto;
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
        long player1 = playerService.save(PlayerDto.builder().name("1").age(10).experience(10).teamId(team1).build()).getId();
        long player2 = playerService.save(PlayerDto.builder().name("2").age(20).experience(10).teamId(team2).build()).getId();
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
        long player1 = playerService.save(PlayerDto.builder().name("1").age(10).experience(10).teamId(team1).build()).getId();
        playerService.updatePlayer(player1, PlayerDto.builder().name("update test").build());
        if (!playerService.getPlayer(player1).getName().equals("update test")) {
            Assertions.fail("updatePlayerName");
        }
    }

    @Test
    void updatePlayerTeam() {
        long team1 = teamService.save("team-1").getId();
        long team2 = teamService.save("team-2").getId();
        long player1 = playerService.save(PlayerDto.builder().name("1").age(10).experience(10).teamId(team1).build()).getId();
        playerService.updatePlayer(player1, PlayerDto.builder().teamId(team2).build());
        if (!playerService.getPlayer(player1).getTeamId().equals(team2)) {
            Assertions.fail("updatePlayerTeam");
        }
    }

    @Test
    void deletePlayer() {
        long team1 = teamService.save("team-1").getId();
        Long player1 = playerService.save(PlayerDto.builder().name("1").age(10).experience(10).teamId(team1).build()).getId();
        playerService.deleteById(player1);
        Set<Long> players = playerService.getAll().stream()
                .map(PlayerDto::getId)
                .filter(player1::equals)
                .collect(toSet());
        if (!players.isEmpty()) {
            Assertions.fail("deletePlayer");
        }
    }

    @Test
    void getPlayerTransferFees() {
        long team1 = teamService.save("team-1").getId();
        long player1 = playerService.save(PlayerDto.builder().name("1").age(10).experience(10).teamId(team1).build()).getId();
        double playerTransferFees = playerService.getPlayerTransferFees(player1);
        double controlFee = (double) (10 * 100000 / 10) * 1.1;
        if (playerTransferFees != controlFee) {
            Assertions.fail("getPlayerTransferFees");
        }
    }
}