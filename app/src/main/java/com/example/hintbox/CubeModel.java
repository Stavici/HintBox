package com.example.hintbox;

import java.util.EnumMap;
import java.util.Map;

/**
 * Реализация модели куба с хранением элементов каждой грани.
 */
public class CubeModel implements ICubeModel {
    private final Map<Side, Cuber[]> faceMap = new EnumMap<>(Side.class);

    /**
     * Сохраняет элементы для указанной грани куба.
     * Клонирует массив, чтобы избежать внешних изменений внутреннего состояния.
     *
     * @param side   сторона куба
     * @param cubers массив элементов грани
     */
    @Override
    public void setFace(Side side, Cuber[] cubers) {
        faceMap.put(side, cloneCubers(cubers));
    }

    /**
     * Возвращает копию массива элементов указанной грани.
     *
     * @param side сторона куба
     * @return копия массива элементов или null, если грань не задана
     */
    @Override
    public Cuber[] getFace(Side side) {
        Cuber[] stored = faceMap.get(side);
        return stored != null ? cloneCubers(stored) : null;
    }

    /**
     * Удаляет данные для указанной грани.
     *
     * @param side сторона куба
     */
    @Override
    public void clearFace(Side side) {
        faceMap.remove(side);
    }

    /**
     * Проверяет, установлены ли данные для заданной грани.
     *
     * @param side сторона куба
     * @return true, если данные присутствуют
     */
    @Override
    public boolean isControlled(Side side) {
        return faceMap.containsKey(side);
    }

    /**
     * Проверяет, заполнены ли все грани куба.
     *
     * @return true, если количество сохранённых граней равно числу сторон
     */
    @Override
    public boolean isComplete() {
        return faceMap.size() == Side.values().length;
    }

    /**
     * Создаёт поверхностную копию массива Cuber для защиты состояния.
     *
     * @param cubers исходный массив
     * @return новый массив с теми же элементами или null
     */
    private Cuber[] cloneCubers(Cuber[] cubers) {
        return cubers != null ? cubers.clone() : null;
    }
}