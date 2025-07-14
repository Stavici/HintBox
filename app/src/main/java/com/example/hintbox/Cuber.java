package com.example.hintbox;

import java.util.function.IntBinaryOperator;

/**
 * Интерфейс для перемещения объекта по координатам.
 */
interface Movable {
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
}

/**
 * Интерфейс для изменения размеров объекта.
 */
interface Resizable {
    int getWidth();
    int getHeight();
    void setWidth(int width);
    void setHeight(int height);
}

/**
 * Интерфейс для управления цветом объекта.
 */
interface Colorable {
    String getColor();
    void setColor(String color);
}

/**
 * Класс модели куба с возможностью движения, изменения размера и цвета.
 */
public class Cube implements Movable, Resizable, Colorable {
    private int x;
    private int y;
    private int width;
    private int height;
    private String color;

    /**
     * @param x начальная координата X
     * @param y начальная координата Y
     * @param width ширина куба
     * @param height высота куба
     * @param color цвет куба (имя или код)
     */
    public Cube(int x, int y, int width, int height, String color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    // Movable
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    // Resizable
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    // Colorable
    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Возвращает максимальное значение по X между текущей координатой и переданным значением.
     *
     * @param otherX значение для сравнения
     * @param comparator функция сравнения двух целых чисел
     * @return результат сравнения
     */
    public int maxX(int otherX, IntBinaryOperator comparator) {
        return comparator.applyAsInt(this.x, otherX);
    }

    /**
     * Возвращает максимальное значение по Y между текущей координатой и переданным значением.
     *
     * @param otherY значение для сравнения
     * @param comparator функция сравнения двух целых чисел
     * @return результат сравнения
     */
    public int maxY(int otherY, IntBinaryOperator comparator) {
        return comparator.applyAsInt(this.y, otherY);
    }
}
