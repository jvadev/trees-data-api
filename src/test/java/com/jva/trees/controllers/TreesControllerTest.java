package com.jva.trees.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.jva.trees.AbstractIT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TreesControllerTest extends AbstractIT {

    private final ObjectMapper mapper;
    private final MockMvc mvc;

    @Test
    public void should_return_aggregated_info_when_tree_in_circle() throws Exception {
        // given:
        treesDataStub();
        // when:
        mvc.perform(get("/api/v1/trees/aggregated-info?centerX=25&centerY=35&radius=500000")
                .contentType(MediaType.APPLICATION_JSON))
                // then:
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createAggregatedInfo()));
    }

    @Test
    public void should_return_empty_info_when_tree_not_in_circle() throws Exception {
        // given:
        treesDataStub();
        // when:
        mvc.perform(get("/api/v1/trees/aggregated-info?centerX=25&centerY=35&radius=100000")
                .contentType(MediaType.APPLICATION_JSON))
                // then:
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}", true));
    }


    private void treesDataStub() {
        stubFor(
                WireMock.get(urlEqualTo("/resource/uvpi-gqnh.json"))
                        .willReturn(
                                aResponse()
                                        .withStatus(OK.value())
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(createTreeInCircle())
                        )
        );
    }

    private String createTreeInCircle() {
        return createJsonFromFile("src/test/resources/files/tree-list.json");
    }

    private String createAggregatedInfo() {
        return createJsonFromFile("src/test/resources/files/aggregated-info.json");
    }

    @SneakyThrows
    private String createJsonFromFile(String filePath) {
        return mapper.readValue(new File(filePath), JsonNode.class).toString();
    }

}