package com.example.hintbox;

import org.opencv.core.Mat;

/**
 * Интерфейс для рисования указателя на кадре видеопотока.
 */
public interface PointerDrawer {
    /**
     * Рисует указатель на переданном кадре.
     *
     * @param frame       кадр в формате OpenCV Mat
     * @param pointerCode код указателя (например, "R", "U", "D" или пустая строка)
     */
    void draw(Mat frame, String pointerCode);
}
