package com.hit.JavaLogger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class JavaLogger {

    public static Logger logger = Logger.getLogger("Restaurant Logger");
    public static FileHandler handler;

    static {
        try {
            handler = new FileHandler("Logs\\RestaurantLogs.log");
            logger.addHandler(handler);
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
