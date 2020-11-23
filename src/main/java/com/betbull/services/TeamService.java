package com.betbull.services;

import com.betbull.dto.TeamDto;
import com.betbull.models.Team;
import com.betbull.persistence.TeamRepository;
import com.google.common.collect.Streams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TeamRepository teamRepository;

    public List<TeamDto> getAll() {
        return Streams.stream(teamRepository.findAll())
                .map(teamMapper::toDto)
                .collect(toList());
    }

    public TeamDto getTeam(long id) {
        return teamRepository.findById(id)
                .map(teamMapper::toDto)
                .orElse(null);
    }

    public TeamDto save(String name) {
        return teamMapper.toDto(teamRepository.save(new Team(name)));
    }

    public TeamDto updateTeam(long id, String name) {
        return teamMapper.toDto(teamRepository.save(new Team(id, name)));
    }

    public void deleteById(long id) {
        teamRepository.deleteById(id);
    }
    
    public boolean existsById(long id) {
        return teamRepository.existsById(id);
    }
}
