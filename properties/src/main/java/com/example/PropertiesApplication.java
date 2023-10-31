package com.example;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.lang.String.format;

public class PropertiesApplication {

    private static final String defaultFileName = "/application.%s";
    private static final String activeFileName = "/application-%s.%s";

    public static void main(String[] args) throws IOException {
        readPropertiesFile();
        readYamlFile();
    }

    /**
     * 讀檔
     *
     * @param fileName
     * @return
     */
    private static InputStream readFile(String fileName) {
        return PropertiesApplication.class.getResourceAsStream(fileName);
    }

    /**
     * 讀取Properties 參數檔
     *
     * @throws IOException
     */
    private static void readPropertiesFile() throws IOException {
        // 讀取預設參數檔
        InputStream defaultProperties = readFile(format(defaultFileName, "properties"));

        Properties properties = new Properties();
        properties.load(defaultProperties);

        // 讀取指定環境參數檔
        String activeProfile = properties.getProperty("active.profile");
        if (null != activeProfile && !activeProfile.isEmpty()) {
            InputStream activeProperties = readFile(format(activeFileName, activeProfile, "properties"));
            if (null != activeProperties) {
                properties.load(activeProperties);
            }
        }

        System.out.println("Properties File: " + properties);
    }

    /**
     * 讀取YAML 參數檔
     */
    private static void readYamlFile() {
        // 讀取預設參數檔
        InputStream yamlStream = readFile(format(defaultFileName, "yml"));

        Yaml yaml = new Yaml();
        Map<String, Object> defaultYaml = yaml.load(yamlStream);

        // 讀取指定環境參數檔
        Map<String, Object> active = (HashMap) defaultYaml.get("active");
        String profile = (String) active.get("profile");
        if (null != profile && !profile.isEmpty()) {
            InputStream activeProperties = readFile(format(activeFileName, profile, "yml"));
            if (null != activeProperties) {
                Map<String, Object> activeYaml = yaml.load(activeProperties);
                refreshMap(defaultYaml, activeYaml);
            }
        }

        System.out.println("Yaml File: " + defaultYaml);
    }

    /**
     * 更新Map key-value 內容，若無key 值新增，反之則取代
     *
     * @param target
     * @param source
     */
    private static void refreshMap(Map<String, Object> target, Map<String, Object> source) {
        source.forEach((key, value) -> {
            if (value instanceof Map) {
                refreshMap((Map) target.get(key), (Map) value);
            } else {
                target.put(key, value);
            }
        });
    }
}