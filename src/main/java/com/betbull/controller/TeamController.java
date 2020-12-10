package com.betbull.controller;

import com.betbull.model.TeamDto;
import com.betbull.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/")
    public ResponseEntity<List<TeamDto>> getTeams() {
        List<TeamDto> teamDtos = teamService.getAll();
        if (!teamDtos.isEmpty()) {
            return ResponseEntity.ok().body(teamDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable long id) {
        TeamDto teamDto = teamService.getTeam(id);
        if (nonNull(teamDto)) {
            return ResponseEntity.ok().body(teamDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{name}")
    public ResponseEntity<TeamDto> addTeam(@PathVariable String name) {
        return ResponseEntity.ok().body(teamService.save(name));
    }

    @PostMapping("/")
    public ResponseEntity<List<TeamDto>> addTeams(@RequestBody List<TeamDto> teamDtoList) {
        return ResponseEntity.ok().body(teamService.save(teamDtoList));
    }

    @PutMapping("/{id}/{name}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable long id, @PathVariable String name) {
        if (teamService.existsById(id)) {
            return ResponseEntity.ok().body(teamService.updateTeam(id, name));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TeamDto> deleteTeam(@PathVariable long id) {
        TeamDto teamDto = teamService.getTeam(id);
        if (nonNull(teamDto)) {
            teamService.deleteById(id);
            return ResponseEntity.ok().body(teamDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/")
    public void deleteTeams(@RequestBody List<TeamDto> teamDtoList) {
        teamDtoList.stream()
                .map(TeamDto::getId)
                .filter(Objects::nonNull)
                .forEach(teamService::deleteById);
    }
}
