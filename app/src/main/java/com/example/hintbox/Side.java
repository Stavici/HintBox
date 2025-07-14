package com.example.hintbox;

/**
 * Перечисление граней куба с методами перехода к следующей и предыдущей грани.
 */
public enum Side {
    FRONT,
    RIGHT,
    BACK,
    LEFT,
    UP,
    DOWN;

    // Кэшированный массив для быстрого доступа к значениям enum
    private static final Side[] VALUES = values();

    /**
     * Возвращает следующую грань по порядку круговой нумерации.
     *
     * @return следующая грань
     */
    public Side nextSide() {
        int nextIndex = (this.ordinal() + 1) % VALUES.length;
        return VALUES[nextIndex];
    }

    /**
     * Возвращает предыдущую грань по порядку круговой нумерации.
     *
     * @return предыдущая грань
     */
    public Side previousSide() {
        int prevIndex = (this.ordinal() + VALUES.length - 1) % VALUES.length;
        return VALUES[prevIndex];
    }
}
