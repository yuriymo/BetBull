package com.betbull.controller;

import com.betbull.model.PlayerDto;
import com.betbull.service.PlayerService;
import com.betbull.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}/transfer-fees")
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

    @GetMapping("/{id}")
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

    @PostMapping("/bulk")
    public ResponseEntity<List<PlayerDto>> savePlayers(@RequestBody List<PlayerDto> playerDtoList) {
        return ResponseEntity.ok().body(playerService.save(playerDtoList));
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<PlayerDto> deletePlayer(@PathVariable long id) {
        PlayerDto PlayerDto = playerService.getPlayer(id);
        if (isNull(PlayerDto)) {
            return ResponseEntity.notFound().build();
        }
        playerService.deleteById(id);
        return ResponseEntity.ok().body(PlayerDto);
    }
}
