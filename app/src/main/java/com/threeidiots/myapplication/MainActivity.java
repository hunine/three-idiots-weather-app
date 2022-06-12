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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int NUM_PAGE = 3;
    private ViewPager2 viewpage;
    private FragmentStateAdapter pageradapter;

    private TextView txtWindy;
    private TextView txtHum;
    private TextView txtFeellike;
    private TextView txtPrecipation;
    private TextView txtTime;
    private Button btnHourly;
    private Button btnDaily;
    private Button btnWeekly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new Gson();
        Weather weathernow = gson.fromJson(getIntent().getStringExtra("now"), Weather.class);
        Weather weatherdaily = gson.fromJson(getIntent().getStringExtra("daily"), Weather.class);
        Weather weatherweekly = gson.fromJson(getIntent().getStringExtra("weekly"), Weather.class);
        System.out.println(String.valueOf(weatherdaily.getWeatherMain().getFeelsLike()));

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
        txtTime = findViewById(R.id.txt_time);

        btnHourly = findViewById(R.id.btn_hourly);
        btnDaily = findViewById(R.id.btn_daily);
        btnWeekly = findViewById(R.id.btn_weekly);




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
                SetText(weatherdaily);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


        btnHourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetText(weathernow);
                txtTime.setText("Weather now");
                ResetButtonColor();
                btnHourly.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });

        btnDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetText(weatherdaily);
                txtTime.setText("Weather tommorow");
                ResetButtonColor();
                btnDaily.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });

        btnWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetText(weatherweekly);
                txtTime.setText("Weather this week");
                ResetButtonColor();
                btnWeekly.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });

    }

    private void SetText(Weather weather){
        txtFeellike.setText(String.valueOf(Math.round(weather.getWeatherMain().getFeelsLike())) + " °C");
        txtHum.setText(String.valueOf(weather.getWeatherMain().getHumidity()) + " %");
        txtWindy.setText(String.valueOf(Math.round(weather.getWind().getSpeed())) + " km/h");
        txtPrecipation.setText(String.valueOf(weather.getWeatherMain().getPressure()) + " MPa");
    }

    private void ResetButtonColor(){
        btnHourly.setTextColor(getResources().getColor(R.color.greytextcolor));
        btnDaily.setTextColor(getResources().getColor(R.color.greytextcolor));
        btnWeekly.setTextColor(getResources().getColor(R.color.greytextcolor));
    }

    private class ScreenSlideAdapter extends FragmentStateAdapter {
        @NonNull
        @Override
        public Fragment createFragment(int position) {

            Gson gson2 = new Gson();
            Weather weathernow2 = gson2.fromJson(getIntent().getStringExtra("now"), Weather.class);

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