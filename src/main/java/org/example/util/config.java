package org.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

}
