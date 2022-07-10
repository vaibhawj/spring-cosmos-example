package com.example.cosmos;

import com.example.cosmos.config.DatabaseAndContextConfiguration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeServiceApplicationTests extends DatabaseAndContextConfiguration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldSave() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/employees").content("{\n" +
                                "\t\"name\": \"Vibe\",\n" +
                                "\t\"city\": \"Bangalore\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode response = mapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), JsonNode.class);
        assertNotNull(UUID.fromString(response.get("id").asText()));
    }
}
