package com.example.task01;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Logger {

    private final String name;
    private Level level = Level.DEBUG;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final Map<String, Logger> loggers = new HashMap<>();

    enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }

    public Logger(String name) {
        this.name = name;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public static Logger getLogger(String name) {
        if (!loggers.containsKey(name)) {
            loggers.put(name, new Logger(name));
        }
        return loggers.get(name);
    }

    private void print(Level messageLevel, String message) {
        if (messageLevel.ordinal() < level.ordinal()) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DATE_FORMAT);
        String time = now.format(TIME_FORMAT);

        System.out.printf("[%s] %s %s %s - %s%n",
                messageLevel, date, time, name, message);
    }

    public void debug(String message) {
        print(Level.DEBUG, message);
    }

    public void debug(String template, Object... args) {
        print(Level.DEBUG, String.format(template, args));
    }

    private void info(String message) {
        print(Level.INFO, message);
    }

    private void info(String template, Object... args) {
        print(Level.INFO, String.format(template, args));
    }

    public void warning(String message) {
        print(Level.WARNING, message);
    }

    public void warning(String template, Object... args) {
        print(Level.WARNING, String.format(template, args));
    }

    public void error(String message) {
        print(Level.ERROR, message);
    }

    public void error(String template, Object... args) {
        print(Level.ERROR, String.format(template, args));
    }

    private void log(Level level, String message) {
        print(level, message);
    }

    private void log(Level level, String template, Object... args) {
        print(level, String.format(template, args));
    }
}
