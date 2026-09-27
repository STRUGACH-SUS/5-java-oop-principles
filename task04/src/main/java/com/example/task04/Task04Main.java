package com.example.task04;

import java.time.temporal.ChronoUnit;

public class Task04Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("myLogger");
        logger.info("Привет, консоль!");

        logger.addHandler(new FileHandler("app.log"));
        logger.warning("Это сообщение уйдет и в консоль, и в файл");

        logger.addHandler(new RotationFileHandler("rotated", ChronoUnit.HOURS));
        logger.error("Это сообщение попадет во все три обработчика");

        Logger memLogger = Logger.getLogger("memLogger");
        MemoryHandler memory = new MemoryHandler(new ConsoleHandler(), 3);
        memLogger.removeHandler(null); // заглушка (можно не вызывать)
        memLogger.addHandler(memory);

        memLogger.info("Сообщение 1 (в буфере)");
        memLogger.info("Сообщение 2 (в буфере)");
        memLogger.info("Сообщение 3 (буфер заполнен → вывелись все три)");

        memLogger.info("Сообщение 4 (снова в буфере)");
        memory.flush(); // принудительно отправляем
    }
}
