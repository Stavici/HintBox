package com.example.hintbox;

import org.opencv.core.Mat;

/**
 * Интерфейс для детектора куба на видеокадре с выходными данными о найденных элементах.
 */
public interface CubeDetector {

    /**
     * Обнаруживает элементы куба на переданном кадре с учётом цветовых настроек и текущей стороны.
     *
     * @param frame       входной кадр в формате OpenCV Mat
     * @param hsvSettings диапазон цветовых настроек HSV для поиска
     * @param controlled  флаг режима управления детекцией
     * @param activeSide  активная сторона куба для поиска
     */
    void detectCube(Mat frame, ColorHSV hsvSettings, boolean controlled, Side activeSide);

    /**
     * Возвращает массив найденных элементов куба после последней обработки кадра.
     *
     * @return массив объектов Cuber
     */
    Cuber[] getDetectedCubers();

    /**
     * Возвращает строковое представление указателя для отображения направления или состояния.
     *
     * @return значение указателя
     */
    String getPointerIndicator();
}