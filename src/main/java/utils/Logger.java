package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final LogLevel minLevel;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public Logger(LogLevel minLevel) {
        this.minLevel = minLevel;
    }

    public void log(LogLevel logLevel, String content) {
        if (logLevel.getLevel() >= minLevel.getLevel()) {
            System.out.printf("%9s | %s | %s\n", logLevel, dtf.format(LocalDateTime.now()), content);
        }
    }
}
