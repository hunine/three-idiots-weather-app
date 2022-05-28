package com.threeidiots.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGE = 3;
    private ViewPager2 viewpage;
    private FragmentStateAdapter pageradapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
//        getActionBar().hide();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        viewpage = findViewById(R.id.pager);





        pageradapter = new ScreenSlideAdapter(this);
        viewpage.setAdapter(pageradapter);
    }

    private class ScreenSlideAdapter extends FragmentStateAdapter {
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new ViewpagerScreen("First Screen");

                case 1:
                    return new ViewpagerScreen("Second Screen");
                case 2:
                    return new ViewpagerScreen("Third Screen");
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGE;
        }

        public ScreenSlideAdapter(MainActivity mainActivity) {
            super(mainActivity);
        }
    }



}