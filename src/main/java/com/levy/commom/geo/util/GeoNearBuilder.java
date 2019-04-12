package com.levy.commom.geo.util;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import static com.levy.commom.geo.util.GeoMapHelper.DECIMAL_FORMAT;

@Data
public class GeoNearBuilder {

    public static final double DEFAULT_GEO_RANG = 0.1;
    private static final int[] LATITUDE_RANGE = {-90, 90};
    private static final int[] LONGITUDE_RANGE = {-180, 180};
    public static final double MAX_STEP = 50;

    private double latitude;
    private double longitude;
    private double rang;
    private int step;
    private Set<String> nearList = new HashSet<>();

    public GeoNearBuilder(double latitude, double longitude) {
        this(latitude, longitude, DEFAULT_GEO_RANG, 0);
    }

    private GeoNearBuilder(double latitude, double longitude, double rang, int step) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rang = rang;
        this.step = step;
    }

    /**
     * generate next big rectangle 9 points than before.
     *
     * @return
     */
    public Set<String> next() {
        //TODO: the center point point should be skip next time.
        nearList.clear();
        int current = step++;
        if (current == 0) {
            //Only return current point when step is 0
            String latKey = DECIMAL_FORMAT.format(latitude);
            String lonKey = DECIMAL_FORMAT.format(longitude);
            nearList.add(latKey + lonKey);
            return nearList;
        }

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                String latKey = DECIMAL_FORMAT.format(latitude - i * rang * current);
                String lonKey = DECIMAL_FORMAT.format(longitude - j * rang * current);
                nearList.add(latKey + lonKey);
            }
        }
        return nearList;
    }

    /**
     * generate next big rectangle 9 points than before.
     *
     * @return
     */
    public Set<String> next2() {
        //TODO: the center point point should be skip next time.
        nearList.clear();
        int current = step++;
        for (int i = -current; i <= current; i++) {
            for (int j = -current; j <= current; j++) {
                if (i == -current || i == current || j == -current || j == current) {
                    String latKey = DECIMAL_FORMAT.format(latitude - i * rang);
                    String lonKey = DECIMAL_FORMAT.format(longitude - j * rang );
                    nearList.add(latKey + lonKey);
                }
            }
        }
        return nearList;
    }

    public boolean hashNext() {
        return step < MAX_STEP;
    }
}
