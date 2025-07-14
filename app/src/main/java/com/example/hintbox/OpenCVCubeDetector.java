package com.example.hintbox;

import org.opencv.core.Mat;

/**
 * Реализация детектора куба на кадре с использованием OpenCV.
 * Сохраняет временные результаты детекции и предоставляет указатель состояния.
 */
public class OpenCVCubeDetector implements CubeDetector {
    private static final int FACE_SIZE = 9;
    private final Cuber[] detectedCubers = new Cuber[FACE_SIZE];
    private String pointerIndicator = "";

    /**
     * Выполняет детекцию элементов куба на кадре.
     * Результаты сохраняются во внутреннем массиве detectedCubers,
     * а строковый указатель состояния обновляется в pointerIndicator.
     *
     * @param frame      текущий кадр в формате Mat
     * @param hsv        настройки HSV для цветового фильтра
     * @param controlled флаг режима управления (участвует ли текущая грань)
     * @param side       активная сторона куба
     */
    @Override
    public void detectCube(Mat frame, ColorHSV hsv, boolean controlled, Side side) {
        // TODO: Реализовать детекцию кубиков на кадре
        // Например: фильтрация по hsv, поиск контуров, определение позиций cubers
        // Сохранить результаты в detectedCubers и обновить pointerIndicator
    }

    /**
     * Возвращает копию массива найденных элементов после последней детекции.
     *
     * @return массив Cuber длины FACE_SIZE
     */
    @Override
    public Cuber[] getDetectedCubers() {
        return detectedCubers.clone();
    }

    /**
     * Возвращает строковый указатель для отображения направления или состояния.
     *
     * @return текущее значение указателя
     */
    @Override
    public String getPointerIndicator() {
        return pointerIndicator;
    }

    /**
     * Устанавливает пользовательский указатель состояния.
     *
     * @param pointer новый указатель
     */
    public void setPointerIndicator(String pointer) {
        this.pointerIndicator = pointer;
    }
}
