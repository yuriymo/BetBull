package com.betbull.controller;

import com.betbull.model.PlayerDto;
import com.betbull.service.PlayerService;
import com.betbull.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;

    @GetMapping("players/{id}/transfer-fees")
    public ResponseEntity<Double> getTransferFees(@PathVariable long id) {
        if (!playerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playerService.getPlayerTransferFees(id));
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDto>> getPlayers() {
        List<PlayerDto> playerDtoList = playerService.getAll();
        if (playerDtoList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playerDtoList);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable long id) {
        PlayerDto playerDto = playerService.getPlayer(id);
        if (isNull(playerDto)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playerDto);
    }

    @PostMapping("/players")
    public ResponseEntity<PlayerDto> savePlayer(@RequestBody PlayerDto playerDto) {
        if (!teamService.existsById(playerDto.getTeamId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playerService.save(playerDto));
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable long id, @RequestBody PlayerDto playerDto) {
        if (!playerService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        PlayerDto body = playerService.updatePlayer(id, playerDto);
        if (isNull(body)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(body);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<PlayerDto> deletePlayer(@PathVariable long id) {
        PlayerDto PlayerDto = playerService.getPlayer(id);
        if (isNull(PlayerDto)) {
            return ResponseEntity.notFound().build();
        }
        playerService.deleteById(id);
        return ResponseEntity.ok().body(PlayerDto);
    }
}
