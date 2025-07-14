package com.example.hintbox;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс отвечает за управление цветами набора кнопок на основе данных куберов.
 */
public class ButtonColorPainter {
    private final Context context;
    private final List<Button> targetButtons = new ArrayList<>();

    /**
     * @param context контекст для доступа к ресурсам
     */
    public ButtonColorPainter(Context context) {
        this.context = context;
    }

    /**
     * Привязывает кнопки, которым будут назначаться цвета.
     * Область видимости переменных минимальна — список кнопок хранится в классе.
     *
     * @param buttons массив кнопок для обработки
     */
    public void setTargetButtons(Button... buttons) {
        targetButtons.clear();
        for (Button btn : buttons) {
            targetButtons.add(btn);
        }
    }

    /**
     * Назначает каждой привязанной кнопке цвет из соответствующего кубера.
     * Каждый метод выполняет единственную задачу: получение ресурса цвета и применение цвета.
     *
     * @param cubers список объектов Cuber с информацией о цвете
     */
    public void applyColors(List<Cuber> cubers) {
        int count = Math.min(targetButtons.size(), cubers.size());
        for (int i = 0; i < count; i++) {
            Button button = targetButtons.get(i);
            String colorName = cubers.get(i).getColor();
            int color = resolveColor(colorName);
            applyColor(button, color);
        }
    }

    /**
     * Сбрасывает фон всех привязанных кнопок в черный цвет.
     */
    public void clearColors() {
        for (Button button : targetButtons) {
            applyColor(button, Color.BLACK);
        }
    }

    /**
     * Получает числовой идентификатор цвета по его имени из ресурсов приложения.
     *
     * @param colorName имя цвета (имя ресурса)
     * @return числовой код цвета
     */
    private int resolveColor(String colorName) {
        int resId = context.getResources()
                .getIdentifier(colorName, "color", context.getPackageName());
        // Если ресурс не найден, возвращаем черный по умолчанию
        if (resId == 0) {
            return Color.BLACK;
        }
        return context.getResources().getColor(resId);
    }

    /**
     * Применяет указанный цвет к фону кнопки.
     *
     * @param button кнопка для изменения
     * @param color  числовой код цвета
     */
    private void applyColor(Button button, int color) {
        button.setBackgroundColor(color);
    }
}