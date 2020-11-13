package com.jva.trees.services;

import com.jva.trees.clients.StreetTreeCensusClient;
import com.jva.trees.models.TreeInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.awt.geom.Point2D.distanceSq;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class TreesService {
    private static final double FEET_VALUE = 3.28;

    private final StreetTreeCensusClient client;

    public Map<String, Long> getTreesAggregation(double centerX, double centerY, double radius) {
        double radiusInFeet = toFeet(radius);
        return client.getTreeList()
                .stream()
                .filter(tree -> tree.getCommonName() != null)
                .filter(tree ->
                        inCircle(tree.getXCoordinate(), tree.getYCoordinate(), centerX, centerY, radiusInFeet))
                .collect(groupingBy(TreeInformation::getCommonName, counting()));
    }

    private boolean inCircle(double pointX, double pointY, double centerX, double centerY, double radius) {
        double distanceSquared = distanceSq(pointX, pointY, centerX, centerY);
        return distanceSquared < radius * radius;
    }

    private double toFeet(double meter) {
        return meter * FEET_VALUE;
    }
}
