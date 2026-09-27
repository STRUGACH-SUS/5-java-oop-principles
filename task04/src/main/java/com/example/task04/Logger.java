package com.example.task04;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logger {

    private final String name;
    private Level level = Level.DEBUG;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final Map<String, Logger> loggers = new HashMap<>();

    private final List<MessageHandler> handlers = new ArrayList<>();

    public enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }

    private Logger(String name) {
        this.name = name;
        handlers.add(new ConsoleHandler());
    }

    public static Logger getLogger(String name) {
        if (!loggers.containsKey(name)) {
            loggers.put(name, new Logger(name));
        }
        return loggers.get(name);
    }

    public void addHandler(MessageHandler handler) {
        handlers.add(handler);
    }

    public void removeHandler(MessageHandler handler) {
        handlers.remove(handler);
    }


    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }


    private void print(Level messageLevel, String message) {
        if (messageLevel.ordinal() < level.ordinal()) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DATE_FORMAT);
        String time = now.format(TIME_FORMAT);

        String formatted = String.format("[%s] %s %s %s - %s",
                messageLevel, date, time, name, message);

        for (MessageHandler handler : handlers) {
            handler.handle(formatted);
        }
    }

    public void debug(String message) {
        print(Level.DEBUG, message);
    }

    public void debug(String template, Object... args) {
        print(Level.DEBUG, String.format(template, args));
    }

    public void info(String message) {
        print(Level.INFO, message);
    }

    public void info(String template, Object... args) {
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

    public void log(Level level, String message) {
        print(level, message);
    }

    public void log(Level level, String template, Object... args) {
        print(level, String.format(template, args));
    }
}