package com.threeidiots.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewpagerScreen extends Fragment {

    public ViewpagerScreen(String str){
        txtScreenNum = str;
    }


    private TextView txt;
    private String txtScreenNum;
    private View inflatedview = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        inflatedview = inflater.inflate(R.layout.fragment, container, false);
        txt = inflatedview.findViewById(R.id.txtScreenNum);
        txt.setText(txtScreenNum);
        txt.setTextColor(Color.GREEN);

        return inflatedview;

    }
}
