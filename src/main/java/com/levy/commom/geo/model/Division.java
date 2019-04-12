package com.levy.commom.geo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Division {
    @JsonProperty("adcode")
    private String adCode;
    @JsonProperty("center")
    private String center;
    @JsonProperty("citycode")
    private String cityCode;
    @JsonProperty("level")
    private String level;
    private String name;
    @JsonProperty("polyline")
    private String bound;
    @JsonProperty("districts")
    private List<Division> districts;
}
