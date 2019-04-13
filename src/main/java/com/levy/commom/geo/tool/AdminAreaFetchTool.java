package com.levy.commom.geo.tool;

import com.google.gson.Gson;
import com.levy.commom.geo.model.Division;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class AdminAreaFetchTool {


    public static void main(String[] args) {

        String url = "http://restapi.amap.com/v3/config/district?key=cd852248570086f7752715ae9a441146&" +
                "subdistrict=1&" +
                "showbiz=false&" +
                "extensions=base&" +
                "keywords=";

        Set<Division> districts = new HashSet<>();
        getDistrictLevel(url, "成都", districts);
        saveToLocal(districts);
        System.out.println(districts);
    }

    public static void saveToLocal(Set<Division> districts) {
        String url = "http://restapi.amap.com/v3/config/district?key=cd852248570086f7752715ae9a441146&" +
                "subdistrict=1&" +
                "showbiz=false&" +
                "extensions=all&" +
                "keywords=";
        for (Division d : districts) {
            System.out.println("Query with:" + d.getName());
            String fileName = d.getAdcode() + "_" + d.getName() + "_" + d.getCitycode() + ".json";
            Path target = Paths.get("src", "main", "resources", "divisions", fileName);
            if (Files.notExists(target)) {
                String result = sendHttp(url + d.getName());
                saveToFile(target, result);
            }
        }
    }

    public static void saveToFile(Path target, String content) {
        try {
            if (Files.notExists(target)) {
                Files.createFile(target);
                log.info("Generate new file with name:" + target.getFileName());
            } else {
                log.info("File already existed with name:" + target.getFileName());
                return;
            }
            BufferedWriter writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("Failed to save file.", e);
        }
    }

    public static void getDistrictLevel(String url, String keywords, Set<Division> districts) {
        String query = url + keywords;
        String result = sendHttp(query);
        System.out.println("Query with:" + keywords);

        Gson gson = new Gson();
        Division response = gson.fromJson(result, Division.class);

        for (Division d : response.getDistricts()) {
            for (Division subD : d.getDistricts()) {
                if ("district".equals(subD.getLevel())) {
                    districts.add(subD);
                } else {
                    getDistrictLevel(url, subD.getName(), districts);
                }
            }
        }
    }

    public static String sendHttp(String uri) {
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
            String replaceResult = result.toString().replaceAll("\"citycode\":\\[\\]", "\"citycode\":\"\"");
            return replaceResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
