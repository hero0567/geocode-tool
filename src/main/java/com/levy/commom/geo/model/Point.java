package com.levy.commom.geo.model;

import lombok.Data;

@Data
public class Point {
    private Double longitude;
    private Double latitude;

    public Point(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}