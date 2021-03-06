package com.betbull.controller;

import com.betbull.model.PlayerDto;
import com.betbull.service.PlayerService;
import com.betbull.service.TeamService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
        if (playerService.isNotExistById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playerService.getPlayerTransferFees(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<PlayerDto>> getPlayers() {
        List<PlayerDto> playerDtoList = playerService.getAll();
        if (playerDtoList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playerDtoList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "find player by id",
            notes = "get corresponded player by id",
            response = PlayerDto.class)
    public ResponseEntity<PlayerDto> getPlayer(@ApiParam(value = "player identifier", required = true) @PathVariable long id) {
        PlayerDto playerDto = playerService.getPlayer(id);
        if (isNull(playerDto)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playerDto);
    }

    @PostMapping("/")
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
        if (playerService.isNotExistById(id)) {
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
