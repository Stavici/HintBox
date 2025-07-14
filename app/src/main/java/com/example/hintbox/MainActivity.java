package com.example.hintbox;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Главная активность: настройка камерного представления,
 * навигации по граням куба,
 * и управление интерфейсом пользователя.
 */
public class MainActivity extends AppCompatActivity
        implements CameraBridgeViewBase.CvCameraViewListener2 {

    private JavaCameraView cameraView;
    private final CubeDetector cubeDetector = new OpenCVCubeDetector();
    private final ICubeModel cubeModel = new CubeModel();
    private ISettingsRepository settingsRepository;
    private ButtonColorPainter colorPainter;
    private PointerDrawer pointerDrawer;
    private Side activeSide;
    private Map<String, ColorHSV> hsvSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSettings();
        initColorButtons();
        initPointerDrawer();
        initCameraView();
        initNavigationBar();
        initFab();

        activeSide = Side.FRONT;
        refreshUi();
    }

    /**
     * Загружает настройки HSV из репозитория.
     */
    private void initSettings() {
        settingsRepository = new ColorSettingsRepository(this);
        hsvSettings = settingsRepository.loadAllSettings();
    }

    /**
     * Инициализирует кнопки для отображения цветов граней.
     */
    private void initColorButtons() {
        List<Button> buttons = new ArrayList<>();
        // Предполагается, что кнопок ровно 9, идентификаторы button1…button9
        for (int i = 1; i <= 9; i++) {
            int id = getResources().getIdentifier("button" + i, "id", getPackageName());
            buttons.add(findViewById(id));
        }
        colorPainter = new ButtonColorPainter(this);
        colorPainter.setTargetButtons(buttons.toArray(new Button[0]));
    }

    /**
     * Инициализирует стратегию рисования указателя.
     */
    private void initPointerDrawer() {
        pointerDrawer = new DefaultPointerDrawer();
    }

    /**
     * Настраивает отображение и обработчик событий камеры.
     */
    private void initCameraView() {
        cameraView = findViewById(R.id.myCameraView);
        cameraView.setCvCameraViewListener(this);
    }

    /**
     * Настраивает нижнюю навигационную панель для переключения граней.
     */
    private void initNavigationBar() {
        BottomNavigationView nav = findViewById(R.id.detection_nav);
        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.next:
                    activeSide = activeSide.next();
                    break;
                case R.id.prev:
                    activeSide = activeSide.prev();
                    break;
                case R.id.clear:
                    cubeModel.clearFaceData(activeSide);
                    break;
                case R.id.start:
                    // TODO: запуск SettingCubeActivity
                    break;
                default:
                    return false;
            }
            refreshUi();
            return true;
        });
    }

    /**
     * Настраивает действие плавающей кнопки для сохранения текущей грани.
     */
    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            cubeModel.setFaceData(activeSide, cubeDetector.getDetectedCubers());
            activeSide = activeSide.next();
            refreshUi();
        });
    }

    /**
     * Обновляет состояние UI: закрашивает кнопки или очищает их.
     */
    private void refreshUi() {
        if (cubeModel.isFaceSet(activeSide)) {
            colorPainter.applyColors(cubeModel.getFaceData(activeSide));
        } else {
            colorPainter.clearColors();
        }
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat frame = inputFrame.rgba();
        hsvSettings.values().forEach(hsv ->
            cubeDetector.detectCube(frame, hsv, cubeModel.isFaceSet(activeSide), activeSide)
        );
        pointerDrawer.draw(frame, cubeDetector.getPointerIndicator());
        return frame;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        // Не используется
    }

    @Override
    public void onCameraViewStopped() {
        // Не используется
    }
}
