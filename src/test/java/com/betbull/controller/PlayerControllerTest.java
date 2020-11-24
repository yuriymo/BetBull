package com.betbull.controller;

import com.betbull.Application;
import com.betbull.models.PlayerDto;
import com.betbull.models.TeamDto;
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

        val teamDto = new Gson().fromJson(mvc.perform(MockMvcRequestBuilders.post("/add-player/test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), TeamDto.class);
        if (isNull(teamDto)) {
            Assertions.fail("add-team - fail");
        }

        val playerDto = new Gson().fromJson(mvc.perform(MockMvcRequestBuilders.post("/add-player?name=player-1&teamId=" + teamDto.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), PlayerDto.class);
        if (isNull(playerDto)) {
            Assertions.fail("add-player - fail");
        }

        val playerDto2 = new Gson().fromJson(mvc.perform(MockMvcRequestBuilders.get("/player/" + playerDto.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), PlayerDto.class);
        if (isNull(playerDto2)) {
            Assertions.fail("get-player - fail");
        }
    }

}