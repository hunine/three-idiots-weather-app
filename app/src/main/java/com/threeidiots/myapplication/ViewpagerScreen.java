package com.threeidiots.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;

public class ViewpagerScreen extends Fragment {

    private ImageView WeatherIcon;
    Context context;
    Double temp;

    public ViewpagerScreen(Double _temp, String packagename, int _videoId, String _imageID, String _description, String _name){
        getPackageName = packagename;
        video_id = _videoId;
        temp = _temp;
        image_id = getResId("icon_" + _imageID, R.drawable.class);
        description = "Today is a " + _description.substring(0, 1).toUpperCase() + _description.substring(1) + " day";
        weather_name = _name;
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        Animation anim2 = AnimationUtils.loadAnimation(context, R.anim.bounce2);
        WeatherIcon.startAnimation(anim);
        txtweathername.startAnimation(anim2);
        startCountAnimation();

    }
    private void startCountAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(15, temp.intValue());
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                txtTemparature.setText(animation.getAnimatedValue().toString() + "°");
            }
        });
        animator.start();
    }

    private int video_id;
    private int image_id;
    private String weather_name;
    private String description;

    private TextView txtdescription;
    private String getPackageName;
    private TextView txtweathername;
    private View inflatedview = null;
    private VideoView videoView;
    private TextView txtTemparature;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        inflatedview = inflater.inflate(R.layout.fragment, container, false);
        videoView = inflatedview.findViewById(R.id.videoview);
        WeatherIcon = inflatedview.findViewById(R.id.weathericon);
        txtdescription = inflatedview.findViewById(R.id.txtdescription);
        context = inflatedview.getContext();
        txtTemparature = inflatedview.findViewById(R.id.txttemparature);
        txtweathername = inflatedview.findViewById(R.id.txtName);


        WeatherIcon.setImageResource(image_id);
        txtdescription.setText(description);
        txtweathername.setText(weather_name);


        String path = "android.resource://" + getPackageName + "/" + video_id;
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        return inflatedview;

    }
}
