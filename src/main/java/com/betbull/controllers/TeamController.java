package com.betbull.controllers;

import com.betbull.dto.TeamDto;
import com.betbull.models.Team;
import com.betbull.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/teams")
    public ResponseEntity<List<TeamDto>> getTeams() {
        List<TeamDto> teamDtos = teamService.getAll();
        if (!teamDtos.isEmpty()) {
            return ResponseEntity.ok().body(teamDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable long id) {
        TeamDto teamDto = teamService.getTeam(id);
        if (nonNull(teamDto)) {
            return ResponseEntity.ok().body(teamDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add-team/{name}")
    public ResponseEntity<TeamDto> addTeam(@PathVariable String name) {
        return ResponseEntity.ok().body(teamService.save(name));
    }

    @PutMapping("/update-team/{id}/{name}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable long id, @PathVariable String name) {
        if (teamService.existsById(id)) {
            return ResponseEntity.ok().body(teamService.updateTeam(id, name));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/del-team/{id}")
    public ResponseEntity<TeamDto> deleteTeam(@PathVariable long id) {
        TeamDto teamDto = teamService.getTeam(id);
        if (nonNull(teamDto)) {
            teamService.deleteById(id);
            return ResponseEntity.ok().body(teamDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
