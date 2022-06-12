package com.threeidiots.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.threeidiots.myapplication.model.Weather;
import com.threeidiots.myapplication.model.WeatherList;
import com.threeidiots.myapplication.viewmodel.WeatherApiService;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WeatherApiService apiService;
    private static final int NUM_PAGE = 3;
    private Weather weathernow;
    private ViewPager2 viewpage;
    private FragmentStateAdapter pageradapter;

    private TextView txtWindy;
    private TextView txtHum;
    private TextView txtFeellike;
    private TextView txtPrecipation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new Gson();
        Weather weathernow = gson.fromJson(getIntent().getStringExtra("key"), Weather.class);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
      
        viewpage = findViewById(R.id.pager);
        txtWindy = findViewById(R.id.txtWindy);
        txtFeellike = findViewById(R.id.txtfeelike);
        txtHum = findViewById(R.id.txtHumidity);
        txtPrecipation = findViewById(R.id.txtPrecitation);




        pageradapter = new ScreenSlideAdapter(this);
        viewpage.setAdapter(pageradapter);
        viewpage.setOffscreenPageLimit(3);





        viewpage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                txtFeellike.setText(String.valueOf(Math.round(weathernow.getWeatherMain().getFeelsLike())) + " °C");
                txtHum.setText(String.valueOf(weathernow.getWeatherMain().getHumidity()) + " %");
                txtWindy.setText(String.valueOf(Math.round(weathernow.getWind().getSpeed())) + " km/h");
                txtPrecipation.setText(String.valueOf(weathernow.getWeatherMain().getPressure()) + " MPa");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    private class ScreenSlideAdapter extends FragmentStateAdapter {
        @NonNull
        @Override
        public Fragment createFragment(int position) {

            Gson gson2 = new Gson();
            Weather weathernow2 = gson2.fromJson(getIntent().getStringExtra("key"), Weather.class);

            switch (position){
                case 0:
                    return new ViewpagerScreen(weathernow2.getWeatherMain().getTemperature(), getPackageName(), R.raw.video1, weathernow2.getWeatherInfoList().get(0).getIcon(), weathernow2.getWeatherInfoList().get(0).getDescription(), weathernow2.getWeatherInfoList().get(0).getName());

                case 1:
                    return new ViewpagerScreen(weathernow2.getWeatherMain().getTemperature(), getPackageName(), R.raw.video2, weathernow2.getWeatherInfoList().get(0).getIcon(),weathernow2.getWeatherInfoList().get(0).getDescription(), weathernow2.getWeatherInfoList().get(0).getName());
                case 2:
                    return new ViewpagerScreen(weathernow2.getWeatherMain().getTemperature(), getPackageName(), R.raw.video3, weathernow2.getWeatherInfoList().get(0).getIcon(), weathernow2.getWeatherInfoList().get(0).getDescription(), weathernow2.getWeatherInfoList().get(0).getName());
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