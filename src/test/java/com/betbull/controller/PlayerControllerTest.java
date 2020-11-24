package com.betbull.controller;

import com.betbull.model.PlayerDto;
import com.betbull.model.TeamDto;
import com.google.gson.Gson;
import lombok.val;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.util.Objects.isNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void addPlayer() throws Exception {
        val teamDto = newTeam("test");
        if (isNull(teamDto)) {
            Assertions.fail("add-team - fail");
        }

        PlayerDto playerDto = PlayerDto.builder().name("1").age(10).experience(10).teamId(teamDto.getId()).build();
        val playerDto1 = newPlayer(playerDto);
        if (isNull(playerDto1)) {
            Assertions.fail("add-player - fail");
        }

        val playerDto2 = getRequest("/players/" + playerDto1.getId(), PlayerDto.class);
        if (isNull(playerDto2)) {
            Assertions.fail("get-player - fail");
        }

        if (!playerDto1.equals(playerDto2)) {
            Assertions.fail("addPlayer - fail");
        }
    }

    @Test
    public void getPlayerTransferFees() throws Exception {
        val teamDto = newTeam("test");
        if (isNull(teamDto)) {
            Assertions.fail("add-team - fail");
        }

        PlayerDto playerDto = PlayerDto.builder().name("1").age(10).experience(10).teamId(teamDto.getId()).build();
        val playerDto1 = newPlayer(playerDto);
        if (isNull(playerDto1)) {
            Assertions.fail("add-player - fail");
        }

        val res = getRequest("/players/" + playerDto1.getId() + "/transfer-fees", Double.class);
        if (!res.equals((10 * 100000 / 10) * 1.1)) {
            Assertions.fail("add-player - fail");
        }
    }

    private <T> T getRequest(String urlTemplate, Class<T> classOfT) throws Exception {
        return new Gson().fromJson(mvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), classOfT);
    }

    private PlayerDto newPlayer(PlayerDto playerDto) throws Exception {
        return new Gson().fromJson(mvc.perform(MockMvcRequestBuilders.post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(playerDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), PlayerDto.class);
    }

    private TeamDto newTeam(final String name) throws Exception {
        return new Gson().fromJson(mvc.perform(MockMvcRequestBuilders.post("/teams/" + name)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), TeamDto.class);
    }
}