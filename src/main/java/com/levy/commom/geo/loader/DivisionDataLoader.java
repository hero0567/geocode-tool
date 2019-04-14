package com.levy.commom.geo.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levy.commom.geo.model.Division;
import com.levy.commom.geo.util.GeoMapHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Objects.isNull;

@Slf4j
public class DivisionDataLoader {

    private ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, List<Division>> load() {

        URL url = getClass().getClassLoader().getResource("divisions");
        if (isNull(url)) {
            log.warn("No resource found in classpath.");
            return emptyMap();
        }

        File directory = new File(url.getFile());
        log.info("Loading resources {}{}", directory.getAbsolutePath(), directory.getName());
        if (isNull(directory) || !directory.isDirectory()) {
            log.warn("config path is not a directory : {}", directory);
            return emptyMap();
        }
        File[] files = directory.listFiles();
        log.info("{} resource files have been found", files.length);
        Map<String, List<Division>> roundGeoMap = new HashMap();
        for (File file : files) {
            if (!file.getName().endsWith(".json")) {
                continue;
            }
            try {
                Division division = objectMapper.readValue(file, Division.class);
                GeoMapHelper.addDivision(roundGeoMap, division.getDistricts().get(0));
            } catch (IOException e) {
                log.error("Failed load raw file: {}", file.getName());
            }
        }
        log.info("Location map load complete.");
        return roundGeoMap;
    }
}
