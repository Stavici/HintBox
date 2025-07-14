package com.example.hintbox;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Репозиторий настроек цветов в формате HSV, сохранённых в SharedPreferences.
 */
public class ColorSettingsRepository implements ISettingsRepository {
    private static final String PREFS_NAME = "ColorSetting";
    private static final String SUFFIX_HL = "Hl";
    private static final String SUFFIX_HH = "Hh";
    private static final String SUFFIX_SL = "Sl";
    private static final String SUFFIX_SH = "Sh";
    private static final String SUFFIX_VL = "Vl";
    private static final String SUFFIX_VH = "Vh";

    private final SharedPreferences prefs;

    public ColorSettingsRepository(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public Map<String, ColorHSV> loadAll() {
        return loadAllColorSettings();
    }

    /**
     * Загружает все настройки HSV по заданным кодам цветов.
     */
    private Map<String, ColorHSV> loadAllColorSettings() {
        Map<String, ColorHSV> settings = new HashMap<>();
        for (ColorCode entry : ColorCode.values()) {
            settings.put(entry.resourceName, loadColorHSV(entry));
        }
        return settings;
    }

    /**
     * Загружает диапазон HSV для одного цвета.
     */
    private ColorHSV loadColorHSV(ColorCode entry) {
        int hl = prefs.getInt(entry.code + SUFFIX_HL, 0);
        int hh = prefs.getInt(entry.code + SUFFIX_HH, 0);
        int sl = prefs.getInt(entry.code + SUFFIX_SL, 0);
        int sh = prefs.getInt(entry.code + SUFFIX_SH, 0);
        int vl = prefs.getInt(entry.code + SUFFIX_VL, 0);
        int vh = prefs.getInt(entry.code + SUFFIX_VH, 0);
        return new ColorHSV(entry.resourceName, hl, hh, sl, sh, vl, vh);
    }

    /**
     * Перечисление кодов и имён ресурсов цветов.
     */
    private enum ColorCode {
        Y("Y", "yellow"),
        G("G", "green"),
        O("O", "orange"),
        W("W", "white"),
        R("R", "red"),
        B("B", "blue");

        final String code;
        final String resourceName;

        ColorCode(String code, String resourceName) {
            this.code = code;
            this.resourceName = resourceName;
        }
    }
}