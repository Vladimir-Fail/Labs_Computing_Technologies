package ru.mpei.fail.lab2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {
    private static final Logger log = LoggerFactory.getLogger(MyLogger.class);
    
    public static void makeLog(String level, String message) {
        switch (level) {
            case "info" -> log.info(message);
            case "warning" -> log.warn(message);
            case "error" -> log.error(message);
        }
    }
}
