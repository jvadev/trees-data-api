package com.jva.trees.controllers;

import com.jva.trees.services.TreesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/trees")
@RequiredArgsConstructor
public class TreesController {
    private final TreesService service;

    @GetMapping(value = "/aggregated-info")
    public Map<String, Long> getTreesAggregatedInfo(@RequestParam double centerX, @RequestParam double centerY,
                                                    @RequestParam double radius) {
        return service.getTreesAggregation(centerX, centerY, radius);
    }
}
