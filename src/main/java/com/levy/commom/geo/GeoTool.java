package com.levy.commom.geo;

import com.levy.commom.geo.model.Division;
import com.levy.commom.geo.model.Point;
import com.levy.commom.geo.util.GeoMapHelper;
import com.levy.commom.geo.util.GeoNearBuilder;
import com.levy.commom.geo.loader.DivisionDataLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class GeoTool {

    private static GeoTool ourInstance = new GeoTool();
    private DivisionDataLoader dataLoader;
    private Map<String, List<Division>> divisionMap;

    private GeoTool() {
        dataLoader = new DivisionDataLoader();
        divisionMap = dataLoader.load();
    }

    public static GeoTool getInstance() {
        return ourInstance;
    }

    public Division findDivisionByLonLat(double lon, double lat) {
        return findDivision(lat, lon);
    }

    public Division findDivision(double lat, double lon) {
        int count = 0;
        Division matchedDiv = null;
        GeoNearBuilder geoNearBuilder = new GeoNearBuilder(lat, lon);
        while (geoNearBuilder.hashNext()) {
            Set<String> nearGeoKey = geoNearBuilder.next2();
            for (String key : nearGeoKey) {
//                System.out.println("key:" + key);
                matchedDiv = findDivision(lat, lon, key);
                count++;
                if (matchedDiv != null) {
                    log.info("{},{} Spend {} times to find division.", lat, lon, count);
                    return matchedDiv;
                }
            }
        }
        log.warn("Failed to find division {} times for {},{}", count, lon, lat);
        return matchedDiv;
    }

    private Division findDivision(double lat, double lon, String geoKey) {
        List<Division> divisions = divisionMap.get(geoKey);
        if (divisions == null) {
            return null;
        }
        for (Division division : divisions) {
            if (findDivision(lat, lon, division)) {
                return division;
            }
        }
        return null;
    }

    private boolean findDivision(double lat, double lon, Division division) {
        Point[] points = GeoMapHelper.extractPoints(division);
        return GeoMapHelper.isInPoly(lat, lon, points);
    }


    public static void main(String[] args) {
        GeoTool tool = GeoTool.getInstance();
        System.out.println(tool.findDivision(30.6556342016, 104.0638732910));

        System.out.println(tool.findDivision(30.6556342016, 104.0638732910));

        System.out.println(tool.findDivision(30.6556342016, 104.0638732910));

        System.out.println(tool.findDivision(30.6556342016, 104.0638732910));

        System.out.println(tool.findDivision(30.6556342016, 104.0638732910));

        System.out.println(tool.findDivision(30.6556342016, 104.0638732910));

        System.out.println(tool.findDivision(30.6556342016, 104.0638732910));
    }
}
