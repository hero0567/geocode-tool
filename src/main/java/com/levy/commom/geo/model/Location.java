package com.levy.commom.geo.model;

import lombok.Data;

@Data
public class Location {
    private String province;
    private String provinceCode;
    private String city;
    private String cityCode;
    private String district;
    private String districtCode;
    private Double latitude;
    private Double longitude;
    private String boundary;
}
