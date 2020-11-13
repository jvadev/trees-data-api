package com.jva.trees;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.jva.trees.clients.StreetTreeCensusClient;
import com.jva.trees.models.TreeInformation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;


public class ApplicationTest extends AbstractIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private HazelcastInstance hazelcastInstance;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private StreetTreeCensusClient client;

    @Test
    public void should_load_context() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    public void should_return_trees_list_from_hazelcast_cache() {
        // given:
        var list = new ArrayList<TreeInformation>();
        list.add(new TreeInformation()
                .setCommonName("red maple")
                .setXCoordinate(1027431.148)
                .setYCoordinate(202756.7687));
        doReturn(list).when(client).getTreeList();
        hazelcastInstance.getMap("trees").put("1234", list);
        // when:
        var response = restTemplate.getForObject(
                String.format("http://localhost:%s/api/v1/trees/aggregated-info?centerX=25&centerY=35&radius=500000", port),
                Map.class);
        // then:
        assertEquals(Map.of("red maple", 1), response);
    }

}
