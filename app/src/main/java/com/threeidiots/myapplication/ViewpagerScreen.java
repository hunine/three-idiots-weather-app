package com.threeidiots.myapplication;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewpagerScreen extends Fragment {

    public ViewpagerScreen(String str, String packagename, int id){
        getPackageName = packagename;
        video_id = id;
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
    }

    private int video_id;
    private TextView txt;
    private String getPackageName;
    private View inflatedview = null;
    private VideoView videoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        inflatedview = inflater.inflate(R.layout.fragment, container, false);
        videoView = inflatedview.findViewById(R.id.videoview);




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
