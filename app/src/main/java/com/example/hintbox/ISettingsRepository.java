package com.example.hintbox;

import java.util.Map;

/**
 * Репозиторий настроек приложения.
 * Предоставляет метод для загрузки всех параметров настроек.
 */
public interface ISettingsRepository {
    /**
     * Загружает все настройки цвета в формате HSV.
     *
     * @return карта, где ключ — имя настройки (например, цвет), значение — объект ColorHSV с диапазоном HSV
     */
    Map<String, ColorHSV> loadAllSettings();
}
