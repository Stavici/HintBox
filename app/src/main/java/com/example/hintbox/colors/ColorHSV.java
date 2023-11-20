package com.example.hintbox.colors;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hintbox.MainActivity;

import org.opencv.core.Scalar;

import java.util.ArrayList;

public class ColorHSV {
    private String name;
    private Scalar sColor;
    private int lowH, highH, lowS, highS, lowV, highV;

    public ColorHSV(String name, int lowLimitHue, int highLimitHue, int lowLimitSaturation, int highLimitSaturation, int lowLimitValue, int highLimitValue, Scalar scalarColor) {
        this.name = name;
        this.lowH = lowLimitHue;
        this.highH = highLimitHue;
        this.lowS = lowLimitSaturation;
        this.highS = highLimitSaturation;
        this.lowV = lowLimitValue;
        this.highV = highLimitValue;
        this.sColor = scalarColor;
    }

    private ColorHSV setSettingHSV(String color, String name, Scalar sColor,AppCompatActivity mainActivity) {
        SharedPreferences setting = mainActivity.getApplicationContext().getSharedPreferences("ColorSetting", 0);
        return new ColorHSV(name,
                setting.getInt(String.format(color + "Hl"), 0),
                setting.getInt(String.format(color + "Hh"), 0),
                setting.getInt(String.format(color + "Sl"), 0),
                setting.getInt(String.format(color + "Sh"), 0),
                setting.getInt(String.format(color + "Vl"), 0),
                setting.getInt(String.format(color + "Vh"), 0),
                sColor);
    }

    public ArrayList<ColorHSV> createColorHSV(AppCompatActivity mainActivity) {
        ArrayList<ColorHSV> colorHSVArrayList = new ArrayList<>();
        colorHSVArrayList.add(this.setSettingHSV("Y", "yellow", new Scalar(255, 0, 0),mainActivity));
        colorHSVArrayList.add(this.setSettingHSV("G", "green", new Scalar(0, 0, 255), mainActivity));
        colorHSVArrayList.add(this.setSettingHSV("O", "orange", new Scalar(0, 255, 0), mainActivity));
        colorHSVArrayList.add(this.setSettingHSV("W", "white", new Scalar(200, 255, 0), mainActivity));
        colorHSVArrayList.add(this.setSettingHSV("R", "red", new Scalar(255, 165, 0), mainActivity));
        colorHSVArrayList.add(this.setSettingHSV("B", "blue", new Scalar(255, 255, 255), mainActivity));
        return colorHSVArrayList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLowHue() {
        return lowH;
    }

    public void setLowHue(int lowH) {
        this.lowH = lowH;
    }

    public int getHighHue() {
        return highH;
    }

    public void setHighHue(int highH) {
        this.highH = highH;
    }

    public int getLowSaturation() {
        return lowS;
    }

    public void setLowSaturation(int lowS) {
        this.lowS = lowS;
    }

    public int getHighSaturation() {
        return highS;
    }

    public void setHighSaturation(int highS) {
        this.highS = highS;
    }

    public int getLowValue() {
        return lowV;
    }

    public void setLowValue(int lowV) {
        this.lowV = lowV;
    }

    public int getHighValue() {
        return highV;
    }

    public void setHighValue(int highV) {
        this.highV = highV;
    }

    @NonNull
    public String toString() {
        return "Color: " + name + " lowH: " + lowH + " hightH: " + highH + " lowS: " + lowS + " hightS: " + highS + " lowV: " + lowV + " hightV: " + highV;
    }

    public Scalar getColor() {
        return new Scalar(highH, highS, highV);
    }


}
