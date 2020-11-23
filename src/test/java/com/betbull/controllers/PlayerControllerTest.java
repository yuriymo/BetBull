package com.betbull.controllers;

import com.betbull.Application;
import com.betbull.dto.PlayerDto;
import com.betbull.dto.TeamDto;
import com.betbull.services.PlayerService;
import com.betbull.services.TeamService;
import com.google.gson.Gson;
import lombok.val;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.util.Objects.isNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void addPlayer() throws Exception {

        val teamDto = new Gson().fromJson(getResponseAsString("/add-team/test"), TeamDto.class);
        if (isNull(teamDto)) {
            Assertions.fail("add-team - fail");
        }

        val playerDto = new Gson().fromJson(getResponseAsString("/add-player?name=player-1&teamId=" + teamDto.getId()), PlayerDto.class);
        if (isNull(playerDto)) {
            Assertions.fail("add-player - fail");
        }

        val playerDto2 = new Gson().fromJson(getResponseAsString("/player/" + playerDto.getId()), PlayerDto.class);
        if (isNull(playerDto2)) {
            Assertions.fail("add-player - fail");
        }
    }

    private String getResponseAsString(String s) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get(s)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}