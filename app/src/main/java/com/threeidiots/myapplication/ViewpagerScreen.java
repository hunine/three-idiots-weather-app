package com.threeidiots.myapplication;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.session.MediaController;
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
        txtScreenNum = str;
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
    private String txtScreenNum;
    private View inflatedview = null;
    private VideoView videoView;
    private MediaController mediaController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        inflatedview = inflater.inflate(R.layout.fragment, container, false);
        txt = inflatedview.findViewById(R.id.txtScreenNum);
        videoView = inflatedview.findViewById(R.id.videoview);



        txt.setText(txtScreenNum);
        txt.setTextColor(Color.GREEN);
        String path = "android.resource://" + getPackageName + "/" + video_id;
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
//                videoView.seekTo(position);
//                if (position == 0) {
//                    videoView.start();
//                }
//                videoView.start();

                // When video Screen change size.
//                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
//                    @Override
//                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//
//                        // Re-Set the videoView that acts as the anchor for the MediaController
//                        mediaController.setAnchorView(videoView);
//                    }
//                });
                mediaPlayer.setLooping(true);
            }
        });

        return inflatedview;

    }
}
