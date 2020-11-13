package com.jva.trees.clients;

import com.jva.trees.models.TreeInformation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "TreeDataApi", url = "${tree.url}")
public interface StreetTreeCensusClient {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Cacheable("trees")
    List<TreeInformation> getTreeList();
}
