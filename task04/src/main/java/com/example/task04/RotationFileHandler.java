package com.example.task04;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Обработчик, который пишет в файл с ротацией:
 * для каждого интервала (например, часа) создаётся свой файл.
 */
public class RotationFileHandler implements MessageHandler {

    private final String baseName;
    private final ChronoUnit rotationUnit;
    private final DateTimeFormatter suffixFormat;

    private LocalDateTime currentIntervalStart;

    /**
     * @param baseName     базовое имя файла (без расширения)
     * @param rotationUnit единица ротации (HOURS, MINUTES, DAYS и т.д.)
     */
    public RotationFileHandler(String baseName, ChronoUnit rotationUnit) {
        this.baseName = baseName;
        this.rotationUnit = rotationUnit;
        this.currentIntervalStart = truncateToUnit(LocalDateTime.now());
        this.suffixFormat = formatterFor(rotationUnit);
    }

    @Override
    public void handle(String message) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime intervalStart = truncateToUnit(now);

        if (!intervalStart.equals(currentIntervalStart)) {
            currentIntervalStart = intervalStart;
        }

        String fileName = buildFileName(currentIntervalStart);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось записать сообщение в файл " + fileName, e);
        }
    }

    /**
     * Обрезает дату-время до начала интервала (например, до начала часа).
     */
    private LocalDateTime truncateToUnit(LocalDateTime dateTime) {
        return dateTime.truncatedTo(rotationUnit);
    }

    /**
     * Формирует имя файла: baseName_yyyy.MM.dd_HH.log и т.п.
     */
    private String buildFileName(LocalDateTime intervalStart) {
        return baseName + "_" + intervalStart.format(suffixFormat) + ".log";
    }

    /**
     * Возвращает формат суффикса в зависимости от единицы ротации.
     */
    private DateTimeFormatter formatterFor(ChronoUnit unit) {
        switch (unit) {
            case MINUTES:
                return DateTimeFormatter.ofPattern("yyyy.MM.dd_HH-mm");
            case HOURS:
                return DateTimeFormatter.ofPattern("yyyy.MM.dd_HH");
            case DAYS:
                return DateTimeFormatter.ofPattern("yyyy.MM.dd");
            default:
                return DateTimeFormatter.ofPattern("yyyy.MM.dd_HH-mm-ss");
        }
    }
}