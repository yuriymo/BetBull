package com.betbull.controller;

import com.betbull.Application;
import com.betbull.model.PlayerDto;
import com.betbull.model.TeamDto;
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

        val teamDto = new Gson().fromJson(mvc.perform(MockMvcRequestBuilders.post("/teams/test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), TeamDto.class);
        if (isNull(teamDto)) {
            Assertions.fail("add-team - fail");
        }

        val playerDto1 = new Gson().fromJson(mvc.perform(MockMvcRequestBuilders.post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(PlayerDto.builder().name("1").age(10).experience(10).teamId(teamDto.getId()).build()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), PlayerDto.class);
        if (isNull(playerDto1)) {
            Assertions.fail("add-player - fail");
        }

        val playerDto2 = new Gson().fromJson(mvc.perform(MockMvcRequestBuilders.get("/players/" + playerDto1.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), PlayerDto.class);
        if (isNull(playerDto2)) {
            Assertions.fail("get-player - fail");
        }

        if (!playerDto1.equals(playerDto2)) {
            Assertions.fail("addPlayer - fail");
        }
    }
}