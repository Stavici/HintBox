package com.example.hintbox;

/**
 * Интерфейс модели куба для хранения и проверки состояний граней.
 */
public interface ICubeModel {
    /**
     * Сохраняет элементы заданной грани.
     *
     * @param side   сторона куба
     * @param cubers массив элементов грани (Cuber[])
     */
    void setFaceData(Side side, Cuber[] cubers);

    /**
     * Возвращает копию массива элементов указанной грани.
     *
     * @param side сторона куба
     * @return массив Cuber или null, если грань не установлена
     */
    Cuber[] getFaceData(Side side);

    /**
     * Удаляет данные указанной грани из модели.
     *
     * @param side сторона куба
     */
    void clearFaceData(Side side);

    /**
     * Проверяет, установлены ли данные для заданной грани.
     *
     * @param side сторона куба
     * @return true, если данные грани присутствуют
     */
    boolean isFaceSet(Side side);

    /**
     * Проверяет, заполнены ли данные для всех граней куба.
     *
     * @return true, если модель содержит все грани
     */
    boolean isComplete();