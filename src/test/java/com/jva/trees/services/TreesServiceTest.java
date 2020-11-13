package com.jva.trees.services;

import com.jva.trees.clients.StreetTreeCensusClient;
import com.jva.trees.models.TreeInformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TreesServiceTest {
    private final StreetTreeCensusClient client = mock(StreetTreeCensusClient.class);
    private final TreesService service = new TreesService(client);

    @BeforeAll
    public void setUp() {
        doReturn(createTreeList()).when(client).getTreeList();
    }

    @Test
    public void should_return_aggregated_info_with_tree_in_circle() {
        // when:
        var result = service.getTreesAggregation(0, 0, 500000);
        // then:
        assertEquals(Map.of("red maple", 1L), result);
    }

    @Test
    public void should_return_empty_info_when_tree_not_in_circle() {
        // when:
        var result = service.getTreesAggregation(0, 0, 100000);
        // then:
        assertEquals(Map.of(), result);
    }

    private List<TreeInformation> createTreeList() {
        return List.of(new TreeInformation()
                .setCommonName("red maple")
                .setXCoordinate(1027431.148)
                .setYCoordinate(202756.7687));
    }
}