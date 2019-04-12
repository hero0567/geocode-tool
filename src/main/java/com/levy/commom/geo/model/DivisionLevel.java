package com.levy.commom.geo.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum DivisionLevel {

    @JsonProperty("country")
    COUNTRY,
    @JsonProperty("province")
    PROVINCE,
    @JsonProperty("city")
    CITY,
    @JsonProperty("district")
    DISTRICT,
    @JsonProperty("street")
    STREET;

    private DivisionLevel() {}

}
