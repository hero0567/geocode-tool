package com.levy.commom.geo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DivisionResponse {
    private String status;
    private String info;
    @JsonProperty("infocode")
    private String infoCode;
    private String count;
    @JsonProperty("districts")
    private List<Division> districts;
}
