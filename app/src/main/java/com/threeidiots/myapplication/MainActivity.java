package com.threeidiots.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.util.Log;
import com.threeidiots.myapplication.model.Weather;
import com.threeidiots.myapplication.model.WeatherList;
import com.threeidiots.myapplication.viewmodel.WeatherApiService;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import android.view.View;
import android.widget.ImageView;
import com.google.android.material.tabs.TabLayout;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WeatherApiService apiService;
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
        viewpage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                System.out.println("Position: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
      
//      API
        apiService = new WeatherApiService();

        apiService.getWeatherList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherList>() {
                    @Override
                    public void onSuccess(@NonNull WeatherList weatherList) {
                        Log.d("DEBUG1", "Success");

                        for (Weather weather: weatherList.getWeathers()) {
                            Log.d("SPACE", "----------------------------------------");
                            Log.d("Datetime Forecasted", weather.getDateTimeForecasted());
                            Log.d("DT", Integer.toString(weather.getDt()));
                            Log.d("Temperature", Double.toString(weather.getWeatherMain().getTemperature()));
                            Log.d("Feels like", Double.toString(weather.getWeatherMain().getFeelsLike()));
                            Log.d("Humidity", Integer.toString(weather.getWeatherMain().getHumidity()));
                            Log.d("Weather name", weather.getWeatherInfoList().get(0).getName());
                            Log.d("Description", weather.getWeatherInfoList().get(0).getDescription());
                            Log.d("Wind speed", Double.toString(weather.getWind().getSpeed()));

                        }
                        Log.d("City", weatherList.getLocation().getCity());
                        Log.d("Country", weatherList.getLocation().getCountry());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                            Log.d("DEBUG1", "Fail " + e.getMessage());
                    }
                });
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