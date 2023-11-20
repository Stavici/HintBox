package com.example.hintbox.objects;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hintbox.Cuber;
import com.example.hintbox.R;

import java.util.ArrayList;

public class Buttons {
    private AppCompatActivity mActivity;
    public Buttons(AppCompatActivity mainActivity){
        this.mActivity = mainActivity;
    }

    public ArrayList<Button> createButtons(int[] listIdButtons){
        ArrayList<Button> buttonList = new ArrayList<>();

        for (Integer button: listIdButtons){
            buttonList.add(this.mActivity.findViewById(button));
        }

        return buttonList;
    }

    public void setStaticButton(Cuber[] cubers, ArrayList<Button> buttonCollection, AppCompatActivity mActivity) {

        int num = 0;
        for (Button button : buttonCollection) {
            paintButton(button, cubers[num].getColor(), mActivity);
            num++;
        }
    }

    public Button paintButton(Button button, String sCube, AppCompatActivity mActivity) {
        switch (sCube) {
            case "yellow":
                button.setBackgroundColor(mActivity.getResources().getColor(R.color.yellow));
                break;
            case "green":
                button.setBackgroundColor(mActivity.getResources().getColor(R.color.green));
                break;
            case "white":
                button.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                break;
            case "blue":
                button.setBackgroundColor(mActivity.getResources().getColor(R.color.blue));
                break;
            case "orange":
                button.setBackgroundColor(mActivity.getResources().getColor(R.color.orange));
                break;
            case "red":
                button.setBackgroundColor(mActivity.getResources().getColor(R.color.red));
                break;
            default:
                button.setBackgroundColor(mActivity.getResources().getColor(R.color.black));
                break;
        }
        return button;
    }
}
