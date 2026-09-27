package com.example.task04;

import java.util.ArrayList;
import java.util.List;

/**
 * Обработчик-прокси: накапливает сообщения в памяти
 * и отправляет их в делегат при:
 * - достижении лимита
 * - явном вызове flush()
 */
public class MemoryHandler implements MessageHandler {

    private final MessageHandler delegate;
    private final int bufferSize;
    private final List<String> buffer = new ArrayList<>();

    public MemoryHandler(MessageHandler delegate, int bufferSize) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("bufferSize должен быть > 0");
        }
        this.delegate = delegate;
        this.bufferSize = bufferSize;
    }

    @Override
    public void handle(String message) {
        buffer.add(message);
        if (buffer.size() >= bufferSize) {
            flush();
        }
    }

    /**
     * Принудительно отправляет все накопленные сообщения в делегат.
     */
    public void flush() {
        for (String msg : buffer) {
            delegate.handle(msg);
        }
        buffer.clear();
    }

    public int size() {
        return buffer.size();
    }
}