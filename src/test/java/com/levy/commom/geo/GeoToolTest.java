package com.levy.commom.geo;

import com.levy.commom.geo.model.Division;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;

public class GeoToolTest {

    static GeoTool geoTool = null;

    @BeforeClass
    public static void before() {
        geoTool = GeoTool.getInstance();
    }

    @Test
    public void testChengDu() {
        Division division = geoTool.findDivisionByLonLat(104.0638732910, 30.6556342016);
        Assert.assertEquals("青羊区", division.getName());

        division = geoTool.findDivisionByLonLat(104.0769195557, 30.6616885329);
        Assert.assertEquals("青羊区", division.getName());

        division = geoTool.findDivisionByLonLat(104.0443038940, 30.6735007704);
        Assert.assertEquals("金牛区", division.getName());

        division = geoTool.findDivisionByLonLat(104.0352058411, 30.6439674695);
        Assert.assertEquals("武侯区", division.getName());

        division = geoTool.findDivisionByLonLat(104.0604400635, 30.5737897329);
        Assert.assertEquals("武侯区", division.getName());

        division = geoTool.findDivisionByLonLat(104.1315078735, 30.7412455585);
        Assert.assertEquals("成华区", division.getName());
    }


    @Test
    public void testFailedCase() {
        Division division = geoTool.findDivisionByLonLat(91.20094, 34.01458);
        Assert.assertEquals("格尔木市", division.getName());
    }

    @Test
    public void testRandomGeoVerifyByGao() {
        Random random = new Random();
        DecimalFormat decimalFormat = new DecimalFormat("#.00000");
        String uri = "http://restapi.amap.com/v3/geocode/regeo?" +
                "key=cd852248570086f7752715ae9a441146&" +
                "radius=1000&" +
                "extensions=all&" +
                "batch=false&" +
                "roadlevel=0&" +
                "location=";


        int latDiff = 53 - 12;  //max latitude and min latitude
        int lonDiff = 123 - 74; //max longitude and min longitude
        for (int i = 0; i < 100; i++) {
            //generate latitude between 12-53 with five-digit decimal point
            //generate longitude between 74-123 with five-digit decimal point
            String lat = 12 + random.nextInt(latDiff) + decimalFormat.format(random.nextDouble());
            String lon = 74 + random.nextInt(lonDiff) + decimalFormat.format(random.nextDouble());
            System.out.println(lon + "," + lat);
            String district = sendHttp(uri + lon + "," + lat);
            Division division = geoTool.findDivisionByLonLat(Double.valueOf(lon), Double.valueOf(lat));
            if (division == null) {
                Assert.assertEquals(district, "[]");
            } else {
                Assert.assertEquals(district, division.getName());
            }
        }
    }

    private String sendHttp(String uri) {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true); // 设置该连接是可以输出的
            connection.setRequestMethod("GET"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line + "\n");
            }
            connection.disconnect();

            System.out.println(result.toString());
            JSONObject json = JSONObject.fromObject(result.toString());
            String district = json.getJSONObject("regeocode").getJSONObject("addressComponent").getString("district");
            System.out.println(district);
            return district;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
