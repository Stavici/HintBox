package com.example.hintbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.slider.RangeSlider;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.EnumMap;
import java.util.Map;

/**
 * Активность для настройки пороговых значений HSV для распознавания цветов.
 */
public class SettingCubeActivity extends AppCompatActivity
        implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "SettingCube";
    private static final Size MAX_FRAME_SIZE = new Size(640, 800);
    private JavaCameraView cameraView;
    private Mat frameMat;
    private Mat maskMat;

    private RangeSlider sliderH;
    private RangeSlider sliderS;
    private RangeSlider sliderV;
    private Scalar hsvMin;
    private Scalar hsvMax;
    private ColorHSV currentHsv;

    private IColorSettingsRepository settingsRepo;
    private final Map<ButtonId, ColorHSV> colorSettings = new EnumMap<>(ButtonId.class);

    private final BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS) {
                Log.i(TAG, "OpenCV initialized");
                cameraView.enableView();
            } else {
                super.onManagerConnected(status);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_cube);

        initRepository();
        loadColorConfigurations();
        bindUIComponents();
        configureCameraView();
        configureNavigation();
        configureSliders();
        selectInitialColor(ButtonId.YELLOW);
    }

    // Инициализация репозитория настроек (DIP)
    private void initRepository() {
        settingsRepo = new SharedPrefsColorRepository(getApplicationContext());
    }

    // Загрузка сохранённых HSV для каждой кнопки
    private void loadColorConfigurations() {
        for (ButtonId id : ButtonId.values()) {
            ColorHSV hsv = settingsRepo.load(id.key, id.name());
            colorSettings.put(id, hsv);
        }
    }

    // Поиск view и настройка клика по кнопкам выбора цвета
    private void bindUIComponents() {
        sliderH = findViewById(R.id.rangeH);
        sliderS = findViewById(R.id.rangeS);
        sliderV = findViewById(R.id.rangeV);

        for (ButtonId id : ButtonId.values()) {
            Button btn = findViewById(id.resId);
            btn.setOnClickListener(v -> selectColor(id));
        }

        findViewById(R.id.save).setOnClickListener(v -> saveAllSettings());
    }

    // Настройка камеры OpenCV
    private void configureCameraView() {
        cameraView = findViewById(R.id.myCameraView);
        cameraView.setMaxFrameSize((int)MAX_FRAME_SIZE.width, (int)MAX_FRAME_SIZE.height);
        cameraView.setVisibility(SurfaceView.VISIBLE);
        cameraView.setCvCameraViewListener(this);
    }

    // Настройка нижней навигации
    private void configureNavigation() {
        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setSelectedItemId(R.id.setting);
        nav.setOnNavigationItemSelectedListener(item -> navigateTo(item));
    }

    private boolean navigateTo(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.solver) {
            startActivity(new Intent(this, SolverActivity.class));
        } else if (id == R.id.cube) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            return true;
        }
        overridePendingTransition(0, 0);
        return true;
    }

    // Настройка поведения слайдеров
    private void configureSliders() {
        setupSlider(sliderH, (low, high) -> currentHsv.setLowH(low).setHighH(high));
        setupSlider(sliderS, (low, high) -> currentHsv.setLowS(low).setHighS(high));
        setupSlider(sliderV, (low, high) -> currentHsv.setLowV(low).setHighV(high));
    }

    private void setupSlider(RangeSlider slider, HsvSetter setter) {
        slider.addOnChangeListener((s, value, fromUser) -> {
            List<Float> vals = s.getValues();
            setter.apply(vals.get(0).intValue(), vals.get(1).intValue());
            updateScalars();
        });
    }

    // Выбор цвета для настройки
    private void selectColor(ButtonId id) {
        currentHsv = colorSettings.get(id);
        updateScalars();
        applyScalarsToSliders();
    }

    private void selectInitialColor(ButtonId id) {
        selectColor(id);
    }

    // Сохранение всех настроек в репозиторий
    private void saveAllSettings() {
        for (Map.Entry<ButtonId, ColorHSV> entry : colorSettings.entrySet()) {
            settingsRepo.save(entry.getKey().key, entry.getValue());
        }
    }

    // Обновление HSV-скаляров для маски
    private void updateScalars() {
        hsvMin = new Scalar(currentHsv.getLowH(), currentHsv.getLowS(), currentHsv.getLowV());
        hsvMax = new Scalar(currentHsv.getHighH(), currentHsv.getHighS(), currentHsv.getHighV());
    }

    // Установка текущих значений на слайдерах
    private void applyScalarsToSliders() {
        sliderH.setValues(currentHsv.getLowH(), currentHsv.getHighH());
        sliderS.setValues(currentHsv.getLowS(), currentHsv.getHighS());
        sliderV.setValues(currentHsv.getLowV(), currentHsv.getHighV());
    }

    // CameraBridgeViewBase callbacks
    @Override public void onCameraViewStarted(int width, int height) {
        frameMat = new Mat(height, width, CvType.CV_8UC4);
        maskMat = new Mat(height, width, CvType.CV_8UC1);
    }
    @Override public void onCameraViewStopped() {
        frameMat.release();
        maskMat.release();
    }
    @Override public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame input) {
        frameMat = input.rgba();
        Core.transpose(frameMat, frameMat);
        Imgproc.resize(frameMat, frameMat, frameMat.size());
        Core.flip(frameMat, frameMat, 1);
        Imgproc.cvtColor(frameMat, frameMat, Imgproc.COLOR_BGR2HSV_FULL);
        Core.inRange(frameMat, hsvMin, hsvMax, maskMat);
        return maskMat;
    }

    /**
     * Идентификаторы кнопок и соответствующие ключи для репозитория.
     */
    private enum ButtonId {
        YELLOW(R.id.yellow, "Y"),
        BLUE(R.id.blue, "B"),
        GREEN(R.id.green, "G"),
        ORANGE(R.id.orange, "O"),
        RED(R.id.red, "R"),
        WHITE(R.id.white, "W");

        final int resId;
        final String key;

        ButtonId(int resId, String key) {
            this.resId = resId;
            this.key = key;
        }
    }

    /**
     * Функциональный интерфейс для установки диапазонов HSV.
     */
    @FunctionalInterface
    private interface HsvSetter {
        ColorHSV apply(int low, int high);
    }

    // Lifecycle
    @Override protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, loaderCallback);
        } else {
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
    @Override protected void onPause() {
        super.onPause();
        if (cameraView != null) cameraView.disableView();
    }
    @Override protected void onDestroy() {
        super.onDestroy();
        if (cameraView != null) cameraView.disableView();
    }
}
