package com.example.hintbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.hintbox.colors.ColorHSV;
import com.example.hintbox.core.Solver;
import com.example.hintbox.objects.Buttons;
import com.example.hintbox.objects.Callbacks;
import com.example.hintbox.objects.CameraView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {



    //private ColorHSV
    //private Cuber[] temp, front, back, left, right, up, down, clear;
    //private String globalSide = "";
    //private Boolean consrolFront, consrolBack, consrolLeft, consrolRight, consrolUp, consrolDown;

    //private FloatingActionButton save;
    //private Button sideView;
    //private String pointer = "";


    private final Callbacks mLoaderCallback = new Callbacks(this);
    private Buttons mBusttons = new Buttons(this);
    private ArrayList<Button> buttonCollection = new ArrayList<>();
    private CameraView mOpenCvCameraView = new CameraView(this);
    private Mat mFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFields();
        initFunc();
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    @Override
    public void onResume() {
        super.onResume();

        String TAG = "OCVSample::Activity";
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
    @Override
    public void onCameraViewStarted(int width, int height) {
        mFrame = new Mat(height, width, CvType.CV_8UC4);
    }
    @Override
    public void onCameraViewStopped() {
        mFrame.release();
    }
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mFrame = inputFrame.rgba();
        Core.transpose(mFrame, mFrame);
        Imgproc.resize(mFrame, mFrame, mFrame.size(), 0, 0, 0);
        Core.flip(mFrame, mFrame, 1);

        List<MatOfPoint> cnt = new ArrayList<MatOfPoint>();

        drawCubeContours(mFrame, redHSV, new Scalar(255, 0, 0));
        drawCubeContours(mFrame, blueHSV, new Scalar(0, 0, 255));
        drawCubeContours(mFrame, greenHSV, new Scalar(0, 255, 0));
        drawCubeContours(mFrame, yellowHSV, new Scalar(200, 255, 0));
        drawCubeContours(mFrame, orangeHSV, new Scalar(255, 165, 0));
        drawCubeContours(mFrame, whiteHSV, new Scalar(255, 255, 255));

        return mFrame;
    }
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();

        sideView = findViewById(R.id.sideView);
        save = findViewById(R.id.fab);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (globalSide) {
                    case "front":
                        consrolFront = true;
                        front = temp;
                        temp = new Cuber[9];
                        globalSide = "right";
                        sideView.setText("right");
                        Log.d("front", String.valueOf(consrolFront));

                        if (consrolRight) {
                            setStaticButton(right);
                        } else {
                            setStaticButton(clear);
                        }

                        pointer = "R";
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                Runnable runnable1 = new Runnable() {
                                    @Override
                                    public void run() {
                                        pointer = "";
                                    }
                                };
                                Thread thr = new Thread(runnable1);
                                try {
                                    thr.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                thr.start();
                            }
                        };
                        Thread thread = new Thread(runnable);
                        thread.start();
                        break;

                    case "right":
                        consrolRight = true;
                        right = temp;
                        temp = new Cuber[9];
                        globalSide = "back";
                        sideView.setText("back");
                        Log.d("front", String.valueOf(consrolRight));

                        if (consrolBack) {
                            setStaticButton(back);
                        } else {
                            setStaticButton(clear);
                        }

                        pointer = "R";
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                Runnable runnable1 = new Runnable() {
                                    @Override
                                    public void run() {
                                        pointer = "";
                                    }
                                };
                                Thread thr = new Thread(runnable1);
                                try {
                                    thr.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                thr.start();
                            }
                        };
                        thread = new Thread(runnable);
                        thread.start();
                        break;

                    case "back":
                        consrolBack = true;
                        back = temp;
                        temp = new Cuber[9];
                        globalSide = "left";
                        sideView.setText("left");
                        Log.d("front", String.valueOf(consrolBack));

                        if (consrolLeft) {
                            setStaticButton(left);
                        } else {
                            setStaticButton(clear);
                        }

                        pointer = "R";
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                Runnable runnable1 = new Runnable() {
                                    @Override
                                    public void run() {
                                        pointer = "";
                                    }
                                };
                                Thread thr = new Thread(runnable1);
                                try {
                                    thr.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                thr.start();
                            }
                        };
                        thread = new Thread(runnable);
                        thread.start();
                        break;

                    case "left":
                        consrolLeft = true;
                        left = temp;
                        temp = new Cuber[9];
                        globalSide = "up";
                        sideView.setText("up");
                        Log.d("front", String.valueOf(consrolLeft));

                        if (consrolUp) {
                            setStaticButton(up);
                        } else {
                            setStaticButton(clear);
                        }

                        pointer = "R";
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                Runnable runnable1 = new Runnable() {
                                    @Override
                                    public void run() {
                                        pointer = "";
                                    }
                                };
                                Thread thr = new Thread(runnable1);
                                try {
                                    thr.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                thr.start();
                            }
                        };
                        thread = new Thread(runnable);
                        thread.start();

                        Runnable runnable2 = new Runnable() {
                            @Override
                            public void run() {
                                Runnable runnable3 = new Runnable() {
                                    @Override
                                    public void run() {
                                        pointer = "U";
                                    }
                                };
                                Thread thr1 = new Thread(runnable3);
                                try {
                                    thr1.sleep(1500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                thr1.start();
                            }
                        };
                        thread = new Thread(runnable2);
                        thread.start();

                        Runnable runnable3 = new Runnable() {
                            @Override
                            public void run() {
                                Runnable runnable4 = new Runnable() {
                                    @Override
                                    public void run() {
                                        pointer = "";
                                    }
                                };
                                Thread thr1 = new Thread(runnable4);
                                try {
                                    thr1.sleep(2500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                thr1.start();
                            }
                        };
                        thread = new Thread(runnable3);
                        thread.start();
                        break;

                    case "up":
                        consrolUp = true;
                        up = temp;
                        temp = new Cuber[9];
                        globalSide = "down";
                        sideView.setText("down");
                        Log.d("front", String.valueOf(consrolUp));

                        if (consrolDown) {
                            setStaticButton(down);
                        } else {
                            setStaticButton(clear);
                        }

                        pointer = "D";
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                Runnable runnable1 = new Runnable() {
                                    @Override
                                    public void run() {
                                        pointer = "";
                                    }
                                };
                                Thread thr = new Thread(runnable1);
                                try {
                                    thr.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                thr.start();
                            }
                        };
                        thread = new Thread(runnable);
                        thread.start();

                        runnable2 = new Runnable() {
                            @Override
                            public void run() {
                                Runnable runnable3 = new Runnable() {
                                    @Override
                                    public void run() {
                                        pointer = "D";
                                    }
                                };
                                Thread thr1 = new Thread(runnable3);
                                try {
                                    thr1.sleep(1500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                thr1.start();
                            }
                        };
                        thread = new Thread(runnable2);
                        thread.start();

                        runnable3 = new Runnable() {
                            @Override
                            public void run() {
                                Runnable runnable4 = new Runnable() {
                                    @Override
                                    public void run() {
                                        pointer = "";
                                    }
                                };
                                Thread thr1 = new Thread(runnable4);
                                try {
                                    thr1.sleep(2500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                thr1.start();
                            }
                        };
                        thread = new Thread(runnable3);
                        thread.start();
                        break;

                    case "down":
                        consrolDown = true;
                        down = temp;
                        temp = new Cuber[9];
                        globalSide = "front";
                        sideView.setText("front");

                        if (consrolFront && consrolRight && consrolBack && consrolLeft && consrolUp && consrolDown) {
                            String cube = "";
                            String u, r, f, d, l, b;

                            u = up[4].getColor();
                            r = right[4].getColor();
                            f = front[4].getColor();
                            d = down[4].getColor();
                            l = left[4].getColor();
                            b = back[4].getColor();

                            for (Cuber cuber : up) {
                                cube = cube + colorConvectore(u, r, f, d, l, b, cuber.getColor());
                            }
                            for (Cuber cuber : right) {
                                cube = cube + colorConvectore(u, r, f, d, l, b, cuber.getColor());
                            }
                            for (Cuber cuber : front) {
                                cube = cube + colorConvectore(u, r, f, d, l, b, cuber.getColor());
                            }
                            for (Cuber cuber : down) {
                                cube = cube + colorConvectore(u, r, f, d, l, b, cuber.getColor());
                            }
                            for (Cuber cuber : left) {
                                cube = cube + colorConvectore(u, r, f, d, l, b, cuber.getColor());
                            }
                            for (Cuber cuber : back) {
                                cube = cube + colorConvectore(u, r, f, d, l, b, cuber.getColor());
                            }
                            Intent intent = new Intent(getApplicationContext(), Solver.class);
                            intent.putExtra("cube", cube);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }
                        break;
                }
            }
        });
    }
    protected void initFunc() {
    }
    protected void initFields() {
        mOpenCvCameraView.createCamera();
        buttonCollection = mBusttons.createButtons(new int[]{R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9});
        ColorHSV colorHSV = null;
        ArrayList<ColorHSV> colorHSVArrayList = colorHSV.createColorHSV(this);
        //TODO: REFACTOR!!!

        if (globalSide == "")
            globalSide = "front";

//        consrolBack = false;
//        consrolDown = false;
//        consrolUp = false;
//        consrolLeft = false;
//        consrolRight = false;

        frontSide = new FrontSide(new Cuber[9]);

        BottomNavigationView bottomNavigationView = findViewById(R.id.detection_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.prev:
                        switch (globalSide) {
                            case "front":
                                globalSide = "down";
                                sideView.setText("down");
                                if (consrolDown)
                                    setStaticButton(down);
                                break;
                            case "down":
                                globalSide = "up";
                                sideView.setText("up");
                                if (consrolUp)
                                    setStaticButton(up);
                                break;
                            case "up":
                                globalSide = "left";
                                sideView.setText("left");
                                if (consrolLeft)
                                    setStaticButton(left);
                                break;
                            case "left":
                                globalSide = "back";
                                sideView.setText("back");
                                if (consrolBack)
                                    setStaticButton(back);
                                break;
                            case "back":
                                globalSide = "right";
                                sideView.setText("right");
                                if (consrolRight)
                                    setStaticButton(right);
                                break;
                            case "right":
                                globalSide = "front";
                                sideView.setText("front");
                                if (consrolFront)
                                    setStaticButton(front);
                                break;
                        }
                        return true;
                    case R.id.start:
                        startActivity(new Intent(getApplicationContext(), SettingCube.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.clear:
                        switch (globalSide) {
                            case "front":
                                consrolFront = false;
                                front = new Cuber[9];
                                break;
                            case "left":
                                consrolLeft = false;
                                left = new Cuber[9];
                                break;
                            case "back":
                                consrolBack = false;
                                back = new Cuber[9];
                                break;
                            case "right":
                                consrolRight = false;
                                right = new Cuber[9];
                                break;
                            case "up":
                                consrolUp = false;
                                up = new Cuber[9];
                                break;
                            case "down":
                                consrolDown = false;
                                down = new Cuber[9];
                                break;
                        }
                        return true;
                    case R.id.next:
                        switch (globalSide) {
                            case "front":
                                globalSide = "right";
                                sideView.setText("right");
                                if (consrolRight)
                                    setStaticButton(right);
                                break;
                            case "right":
                                globalSide = "back";
                                sideView.setText("back");
                                if (consrolBack)
                                    setStaticButton(back);
                                break;
                            case "back":
                                globalSide = "left";
                                sideView.setText("left");
                                if (consrolLeft)
                                    setStaticButton(left);
                                break;
                            case "left":
                                globalSide = "up";
                                sideView.setText("up");
                                if (consrolUp)
                                    setStaticButton(up);
                                break;
                            case "up":
                                globalSide = "down";
                                sideView.setText("down");
                                if (consrolDown)
                                    setStaticButton(down);
                                break;
                            case "down":
                                globalSide = "front";
                                sideView.setText("front");
                                if (consrolFront)
                                    setStaticButton(front);
                                break;
                        }
                        return true;
                }
                return false;
            }
        });
    }
    public String colorConvectore(String u_, String r_, String f_, String d_, String l_, String b_, String cube) {
        if (cube == u_) {
            return "U";
        }
        if (cube == r_) {
            return "R";
        }
        if (cube == f_) {
            return "F";
        }
        if (cube == d_) {
            return "D";
        }
        if (cube == l_) {
            return "L";
        }
        if (cube == b_) {
            return "B";
        }
        return "";
    }
    public List<MatOfPoint> findCubeContours(Mat mMat, ColorHSV color, Boolean control) {
        List<MatOfPoint> contour = new ArrayList<MatOfPoint>();
        List<MatOfPoint> result = new ArrayList<MatOfPoint>();
        Mat mInRange = new Mat();

        Scalar scalarLow = new Scalar(color.getLowHue(), color.getLowSaturation(), color.getLowValue());
        Scalar scalarHight = new Scalar(color.getHighHue(), color.getHighSaturation(), color.getHighValue());

        Imgproc.cvtColor(mMat, mInRange, Imgproc.COLOR_BGR2HSV_FULL);
        Core.inRange(mInRange, scalarLow, scalarHight, mInRange);
        Imgproc.GaussianBlur(mInRange, mInRange, new Size(5, 5), 0);
        Imgproc.findContours(mInRange, contour, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE, new Point(0, 0));
        mInRange.release();

        Rect rectContours = new Rect();
        int xContours, yContours;

        for (int idx = 0; idx < contour.size(); idx++)
            if ((Imgproc.contourArea(contour.get(idx)) > 15000) && (Imgproc.contourArea(contour.get(idx)) < 30000)) {
                result.add(contour.get(idx));
                rectContours = Imgproc.boundingRect(contour.get(idx));
                xContours = rectContours.x;
                yContours = rectContours.y;

                if ((xContours > 0) && (xContours < 150) && (yContours > 0) && (yContours < 120)) {
                    saveResultColor(rectContours.x, rectContours.y, rectContours.width, rectContours.height, color.getName(), globalSide, 0);
                    if (!control)
                        paintButton(button1, color.getName());
                }
                if ((xContours > 180) && (xContours < 330) && (yContours > 0) && (yContours < 120)) {
                    saveResultColor(rectContours.x, rectContours.y, rectContours.width, rectContours.height, color.getName(), globalSide, 1);
                    if (!control)
                        paintButton(button2, color.getName());
                }
                if ((xContours > 380) && (xContours < 540) && (yContours > 0) && (yContours < 120)) {
                    saveResultColor(rectContours.x, rectContours.y, rectContours.width, rectContours.height, color.getName(), globalSide, 2);
                    if (!control)
                        paintButton(button3, color.getName());
                }
                if ((xContours > 0) && (xContours < 150) && (yContours > 200) && (yContours < 350)) {
                    saveResultColor(rectContours.x, rectContours.y, rectContours.width, rectContours.height, color.getName(), globalSide, 3);
                    if (!control)
                        paintButton(button4, color.getName());
                }
                if ((xContours > 180) && (xContours < 330) && (yContours > 200) && (yContours < 350)) {
                    saveResultColor(rectContours.x, rectContours.y, rectContours.width, rectContours.height, color.getName(), globalSide, 4);
                    if (!control)
                        paintButton(button5, color.getName());
                }
                if ((xContours > 380) && (xContours < 540) && (yContours > 200) && (yContours < 350)) {
                    saveResultColor(rectContours.x, rectContours.y, rectContours.width, rectContours.height, color.getName(), globalSide, 5);
                    if (!control)
                        paintButton(button6, color.getName());
                }
                if ((xContours > 0) && (xContours < 150) && (yContours > 370) && (yContours < 540)) {
                    saveResultColor(rectContours.x, rectContours.y, rectContours.width, rectContours.height, color.getName(), globalSide, 6);
                    if (!control)
                        paintButton(button7, color.getName());
                }
                if ((xContours > 180) && (xContours < 330) && (yContours > 370) && (yContours < 540)) {
                    saveResultColor(rectContours.x, rectContours.y, rectContours.width, rectContours.height, color.getName(), globalSide, 7);
                    if (!control)
                        paintButton(button8, color.getName());
                }
                if ((xContours > 380) && (xContours < 540) && (yContours > 370) && (yContours < 540)) {
                    saveResultColor(rectContours.x, rectContours.y, rectContours.width, rectContours.height, color.getName(), globalSide, 8);
                    if (!control)
                        paintButton(button9, color.getName());
                }
            }
        return result;
    }
    public void saveResultColor(int x_, int y_, int w_, int h_, String color, String side, int index) {
        Cuber item = new Cuber(x_, y_, w_, h_, color);
        temp[index] = item;
    }
    public List<MatOfPoint> drawCubeContours(Mat mMat, ColorHSV colorHSV, Scalar colorLine) {
        Rect rectContours = new Rect();
        List<MatOfPoint> cnt = new ArrayList<MatOfPoint>();
        switch (globalSide) {
            case "front":
                cnt = findCubeContours(mMat, colorHSV, consrolFront);
                break;
            case "left":
                cnt = findCubeContours(mMat, colorHSV, consrolLeft);
                break;
            case "back":
                cnt = findCubeContours(mMat, colorHSV, consrolBack);
                break;
            case "right":
                cnt = findCubeContours(mMat, colorHSV, consrolRight);
                break;
            case "up":
                cnt = findCubeContours(mMat, colorHSV, consrolUp);
                break;
            case "down":
                cnt = findCubeContours(mMat, colorHSV, consrolDown);
                break;
        }

        switch (pointer) {
            case "":
                for (MatOfPoint point : cnt) {
                    rectContours = Imgproc.boundingRect(point);
                    Imgproc.rectangle(mMat, rectContours.tl(), rectContours.br(), colorLine, 5);
                }
                break;
            case "R":
                Point fPoint = new Point();
                Point sPoint = new Point();
                fPoint.x = 120;
                fPoint.y = 200;
                sPoint.x = 520;
                sPoint.y = 200;
                Point hightFPoint = new Point();
                hightFPoint.x = 480;
                hightFPoint.y = 160;
                Point lowFPoint = new Point();
                lowFPoint.x = 480;
                lowFPoint.y = 240;
                Imgproc.line(mMat, fPoint, sPoint, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, hightFPoint, sPoint, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, lowFPoint, sPoint, new Scalar(0, 70, 173), 5);

                Point fPoint2 = new Point();
                Point sPoint2 = new Point();
                fPoint2.x = 120;
                fPoint2.y = 400;
                sPoint2.x = 520;
                sPoint2.y = 400;
                Point hightFPoint2 = new Point();
                hightFPoint2.x = 480;
                hightFPoint2.y = 360;
                Point lowFPoint2 = new Point();
                lowFPoint2.x = 480;
                lowFPoint2.y = 440;
                Imgproc.line(mMat, fPoint2, sPoint2, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, hightFPoint2, sPoint2, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, lowFPoint2, sPoint2, new Scalar(0, 70, 173), 5);
                break;

            case "U":
                Point fPointUp = new Point();
                Point sPointUp = new Point();
                fPointUp.x = 200;
                fPointUp.y = 120;
                sPointUp.x = 200;
                sPointUp.y = 520;
                Point hightFPointUp = new Point();
                hightFPointUp.x = 160;
                hightFPointUp.y = 200;
                Point lowFPointUp = new Point();
                lowFPointUp.x = 240;
                lowFPointUp.y = 200;
                Imgproc.line(mMat, fPointUp, sPointUp, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, hightFPointUp, fPointUp, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, lowFPointUp, fPointUp, new Scalar(0, 70, 173), 5);

                Point fPoint2Up2 = new Point();
                Point sPoint2Up2 = new Point();
                fPoint2Up2.x = 400;
                fPoint2Up2.y = 120;
                sPoint2Up2.x = 400;
                sPoint2Up2.y = 520;
                Point hightFPoint2Up2 = new Point();
                hightFPoint2Up2.x = 360;
                hightFPoint2Up2.y = 200;
                Point lowFPoint2Up2 = new Point();
                lowFPoint2Up2.x = 440;
                lowFPoint2Up2.y = 200;
                Imgproc.line(mMat, fPoint2Up2, sPoint2Up2, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, hightFPoint2Up2, fPoint2Up2, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, lowFPoint2Up2, fPoint2Up2, new Scalar(0, 70, 173), 5);
                break;

            case "D":
                Point fPointDown = new Point();
                Point sPointDown = new Point();
                fPointDown.x = 200;
                fPointDown.y = 120;
                sPointDown.x = 200;
                sPointDown.y = 520;
                Point hightFPointDown = new Point();
                hightFPointDown.x = 160;
                hightFPointDown.y = 480;
                Point lowFPointDown = new Point();
                lowFPointDown.x = 240;
                lowFPointDown.y = 480;
                Imgproc.line(mMat, fPointDown, sPointDown, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, hightFPointDown, sPointDown, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, lowFPointDown, sPointDown, new Scalar(0, 70, 173), 5);

                Point fPoint2Down2 = new Point();
                Point sPoint2Down2 = new Point();
                fPoint2Down2.x = 400;
                fPoint2Down2.y = 120;
                sPoint2Down2.x = 400;
                sPoint2Down2.y = 520;
                Point hightFPoint2Down2 = new Point();
                hightFPoint2Down2.x = 360;
                hightFPoint2Down2.y = 480;
                Point lowFPoint2Down2 = new Point();
                lowFPoint2Down2.x = 440;
                lowFPoint2Down2.y = 480;
                Imgproc.line(mMat, fPoint2Down2, sPoint2Down2, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, hightFPoint2Down2, sPoint2Down2, new Scalar(0, 70, 173), 5);
                Imgproc.line(mMat, lowFPoint2Down2, sPoint2Down2, new Scalar(0, 70, 173), 5);
                break;
        }
        return cnt;
    }
}