package com.betbull.controllers;

import com.betbull.dto.PlayerDto;
import com.betbull.services.PlayerService;
import com.betbull.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.nonNull;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDto>> getPlayers() {
        List<PlayerDto> playerDtos = playerService.getAll();
        if (!playerDtos.isEmpty()) {
            return ResponseEntity.ok().body(playerDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable long id) {
        PlayerDto PlayerDto = playerService.getPlayer(id);
        if (nonNull(PlayerDto)) {
            return ResponseEntity.ok().body(PlayerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add-player")
    public ResponseEntity<PlayerDto> addPlayer(@Param("name") String name, @Param("teamId") long teamId) {
        if (teamService.existsById(teamId)) {
            return ResponseEntity.ok().body(playerService.save(name, teamId));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-player/{id}/{name}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable long id, @PathVariable String name) {
        PlayerDto PlayerDto = playerService.getPlayer(id);
        if (playerService.existsById(id)) {
            return ResponseEntity.ok().body(playerService.updatePlayer(id, name));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/del-player/{id}")
    public ResponseEntity<PlayerDto> deletePlayer(@PathVariable long id) {
        PlayerDto PlayerDto = playerService.getPlayer(id);
        if (nonNull(PlayerDto)) {
            playerService.deleteById(id);
            return ResponseEntity.ok().body(PlayerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
