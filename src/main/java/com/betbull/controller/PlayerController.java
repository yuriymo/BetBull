package com.betbull.controller;

import com.betbull.models.PlayerDto;
import com.betbull.service.PlayerService;
import com.betbull.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("players/{id}/transfer-fees")
    public ResponseEntity<Double> getTransferFees(@PathVariable long id) {
        PlayerDto playerDto = playerService.getPlayer(id);
        if (nonNull(playerDto)) {
            double transferFees = (playerDto.getExperience() * 100000 / playerDto.getAge()) * 1.1;
            return ResponseEntity.ok().body(transferFees);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDto>> getPlayers() {
        List<PlayerDto> playerDtoList = playerService.getAll();
        if (!playerDtoList.isEmpty()) {
            return ResponseEntity.ok().body(playerDtoList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable long id) {
        PlayerDto playerDto = playerService.getPlayer(id);
        if (nonNull(playerDto)) {
            return ResponseEntity.ok().body(playerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/players/add")
    public ResponseEntity<PlayerDto> savePlayer(@RequestBody PlayerDto playerDto) {
        if (teamService.existsById(playerDto.getTeamId())) {
            return ResponseEntity.ok().body(playerService.save(playerDto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/players/update/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable long id, @RequestBody PlayerDto playerDto) {
        if (playerService.existsById(id)) {
            return ResponseEntity.ok().body(playerService.updatePlayer(id, playerDto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/players/del/{id}")
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
