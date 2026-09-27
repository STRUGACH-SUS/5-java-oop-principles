package com.example.task04;

/**
 * Обработчик сообщений логгера.
 * Реализации определяют, КУДА и КАК попадает сообщение.
 */
public interface MessageHandler {

    /**
     * Обработать сообщение.
     *
     * @param message уже отформатированное сообщение
     */
    void handle(String message);
}