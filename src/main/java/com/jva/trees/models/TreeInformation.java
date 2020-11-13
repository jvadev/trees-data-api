package com.jva.trees.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Accessors(chain = true)
public class TreeInformation implements Serializable {
    @JsonProperty(value = "spc_common")
    private String commonName;
    @JsonProperty(value = "x_sp")
    private double xCoordinate;
    @JsonProperty(value = "y_sp")
    private double yCoordinate;
}
