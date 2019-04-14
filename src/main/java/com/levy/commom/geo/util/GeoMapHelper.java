package com.levy.commom.geo.util;

import com.levy.commom.geo.model.Division;
import com.levy.commom.geo.model.DivisionLevel;
import com.levy.commom.geo.model.Point;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

public class GeoMapHelper {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.0");


    public static void addDivision(Map<String, List<Division>> roundGeoMap, Division d) {
        if (!"district".equals(d.getLevel())) {
            return;
        }

        String k = getKey(d);
        List<Division> dList = roundGeoMap.get(k);
        if (dList == null) {
            dList = new ArrayList<>();
            dList.add(d);
            roundGeoMap.put(k, dList);
        } else {
            dList.add(d);
        }
    }

    public static String getKey(Division d) {
        String center = d.getCenter();
        String[] point = center.split(",");
        if (point.length != 2) {
            return center;
        }
        String lat = point[1];
        String lon = point[0];
        return getKey(Double.valueOf(lat), Double.valueOf(lon));
    }

    public static String getKey(double lat, double lon) {
        return DECIMAL_FORMAT.format(lat) + DECIMAL_FORMAT.format(lon);
    }


    public static boolean isInPoly(double lat, double lon, Point[] points) {
        int iSum, iCount, i;
        double dLon1 = 0, dLon2 = 0, dLat1 = 0, dLat2 = 0, dLon;
        if (points.length < 3) {
            return false;
        }
        iSum = 0;
        iCount = points.length;
        for (i = 0; i < iCount; i++) {
            if (i == iCount - 1) {
                dLon1 = points[i].getLongitude();
                dLat1 = points[i].getLatitude();
                dLon2 = points[0].getLongitude();
                dLat2 = points[0].getLatitude();
            } else {
                dLon1 = points[i].getLongitude();
                dLat1 = points[i].getLatitude();
                dLon2 = points[i + 1].getLongitude();
                dLat2 = points[i + 1].getLatitude();
            }
            // 以下语句判断A点是否在边的两端点的水平平行线之间，在则可能有交点，开始判断交点是否在左射线上
            if (((lat >= dLat1) && (lat < dLat2)) || ((lat >= dLat2) && (lat < dLat1))) {
                if (Math.abs(dLat1 - dLat2) > 0) {
                    //得到 A点向左射线与边的交点的x坐标：
                    dLon = dLon1 - ((dLon1 - dLon2) * (dLat1 - lat)) / (dLat1 - dLat2);
                    // 如果交点在A点左侧（说明是做射线与 边的交点），则射线与边的全部交点数加一：
                    if (dLon < lon) {
                        iSum++;
                    }
                }
            }
        }
        if ((iSum % 2) != 0) {
            return true;
        }
        return false;
    }

    public static Point[] extractPoints(Division division) {
        List<Point> points = new ArrayList<>();
        String bound = division.getBound();
        if (StringUtils.isNotBlank(bound)) {
            String[] groups = bound.split(";");
            for (int i = 0; i < groups.length; i++) {
                String group = groups[i];
                String[] latLon = group.split(",");
                if (latLon.length == 2) {
                    double lon = Double.valueOf(latLon[0]);
                    double lat = Double.valueOf(latLon[1]);
                    Point point = new Point(lon, lat);
                    points.add(point);
                }
            }
        }
        return points.toArray(new Point[points.size()]);
    }
}
