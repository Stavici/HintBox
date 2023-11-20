package com.example.hintbox.objects;

import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hintbox.R;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;

public class CameraView extends CameraBridgeViewBase {

    private AppCompatActivity mActivity;
    private CameraBridgeViewBase openCvCameraView;
    public CameraView(AppCompatActivity mainActivity){
        super();
        this.mActivity = mainActivity;
    }

    public void createCamera(){
        openCvCameraView = (JavaCameraView) this.mActivity.findViewById(R.id.myCameraView);
        openCvCameraView.setMaxFrameSize(640, 800);
        openCvCameraView.setVisibility(SurfaceView.VISIBLE);
        openCvCameraView.setCvCameraViewListener((CameraBridgeViewBase.CvCameraViewListener2) this.mActivity);
    }

    @Override
    protected boolean connectCamera(int width, int height) {
        return false;
    }

    @Override
    protected void disconnectCamera() {

    }


}
