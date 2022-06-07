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

    private static final int REQUEST_CHECK_SETTING = 100;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final String TAG = MainActivity.class.getSimpleName();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private boolean requestingLocationUpdates = false;
    double latitude = 0.0;
    double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
//      getActionBar().hide();
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
                txtFeellike.setText(String.valueOf(weathernow.getWeatherMain().getFeelsLike()));
                txtHum.setText(String.valueOf(weathernow.getWeatherMain().getHumidity()));
//                txtPrecipation.setText(String.valueOf(weathernow.getWeatherMain().get()));
//                txtFeellike.setText(String.valueOf(weathernow.getWeatherMain().getFeelsLike()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });



//      API
//        apiService = new WeatherApiService();
//
//        apiService.getWeatherList()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<WeatherList>() {
//                    @Override
//                    public void onSuccess(@NonNull WeatherList weatherList) {
//                        Log.d("DEBUG1", "Success");
//                        weathernow = weatherList.getWeathers().get(0);
//
//                        for (Weather weather: weatherList.getWeathers()) {
//                            Log.d("SPACE", "----------------------------------------");
//                            Log.d("Datetime Forecasted", weather.getDateTimeForecasted());
//                            Log.d("DT", Integer.toString(weather.getDt()));
//                            Log.d("Temperature", Double.toString(weather.getWeatherMain().getTemperature()));
//                            Log.d("Feels like", Double.toString(weather.getWeatherMain().getFeelsLike()));
//                            Log.d("Humidity", Integer.toString(weather.getWeatherMain().getHumidity()));
//                            Log.d("Weather name", weather.getWeatherInfoList().get(0).getName());
//                            Log.d("Description", weather.getWeatherInfoList().get(0).getDescription());
//                            Log.d("Wind speed", Double.toString(weather.getWind().getSpeed()));
//
//                        }
//                        Log.d("City", weatherList.getLocation().getCity());
//                        Log.d("Country", weatherList.getLocation().getCountry());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                            Log.d("DEBUG1", "Fail " + e.getMessage());
//                    }
//                });
    }

    private class ScreenSlideAdapter extends FragmentStateAdapter {
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new ViewpagerScreen("First Screen", getPackageName(), R.raw.video1);

                case 1:
                    return new ViewpagerScreen("Second Screen", getPackageName(), R.raw.video2);
                case 2:
                    return new ViewpagerScreen("Third Screen", getPackageName(), R.raw.video3);
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