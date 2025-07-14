```java
package com.example.hintbox;

import org.opencv.core.Mat;
import java.util.EnumMap;
import java.util.Map;

/**
 * Рисует указатель на исходном кадре с использованием стратегий по типу указателя.
 */
public class DefaultPointerDrawer implements PointerDrawer {
    private final Map<PointerType, PointerDrawer> drawerMap = new EnumMap<>(PointerType.class);

    /**
     * Инициализирует набор стратегий для рисования указателя.
     */
    public DefaultPointerDrawer() {
        for (PointerType type : PointerType.values()) {
            drawerMap.put(type, createDrawer(type));
        }
    }

    @Override
    public void draw(Mat frame, String pointer) {
        PointerType type = PointerType.fromCode(pointer);
        drawerMap.get(type).draw(frame, pointer);
    }

    /**
     * Создаёт конкретную стратегию на основе типа указателя.
     *
     * @param type тип указателя
     * @return реализация PointerDrawer для данного типа
     */
    private PointerDrawer createDrawer(PointerType type) {
        switch (type) {
            case R: return new RPointerDrawer();
            case U: return new UPointerDrawer();
            case D: return new DPointerDrawer();
            case NONE:
            default: return new NoPointerDrawer();
        }
    }

    /**
     * Типы поддерживаемых указателей.
     */
    private enum PointerType {
        NONE(""),
        R("R"),
        U("U"),
        D("D");

        private final String code;

        PointerType(String code) {
            this.code = code;
        }

        /**
         * Находит тип по строковому коду, возвращает NONE, если неизвестен.
         */
        static PointerType fromCode(String code) {
            for (PointerType t : values()) {
                if (t.code.equals(code)) {
                    return t;
                }
            }
            return NONE;
        }
    }
}
```
