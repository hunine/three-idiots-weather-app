package com.threeidiots.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ValueAnimator;
import android.os.Bundle;
import com.google.gson.Gson;
import com.threeidiots.myapplication.model.Weather;
import com.threeidiots.myapplication.model.WeatherList;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final int NUM_PAGE = 3;
    private ViewPager2 viewpage;
    private int viewpager_position =0;
    private FragmentStateAdapter pageradapter;

    private TextView txtWindy;
    private TextView txtHum;
    private TextView txtFeellike;
    private TextView txtPrecipation;
    private TextView txtCity;
    private TextView txtTime;

    private ImageButton btnLocation;
    private Button btnHourly;
    private Button btnDaily;
    private Button btnWeekly;

    private int pre_pressure =0;
    private int pre_feellike =0;
    private int pre_windy =0;
    private int pre_humidity =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new Gson();
        WeatherList weather1 = gson.fromJson(getIntent().getStringExtra("city1"), WeatherList.class);
//        Weather weathernow1 = weather1.getWeathers().get(0);
        Weather weatherdaily1 = weather1.getWeathers().get(7);
//        Weather weatherweekly1 = weather1.getWeathers().get(32);
//        System.out.println(weather1.getLocation().getCity().toString());


        WeatherList weather2 = gson.fromJson(getIntent().getStringExtra("city2"), WeatherList.class);
//        Weather weathernow2 = weather2.getWeathers().get(0);
//        Weather weatherdaily2 = weather2.getWeathers().get(7);
//        Weather weatherweekly2 = weather2.getWeathers().get(32);


        WeatherList weather3 = gson.fromJson(getIntent().getStringExtra("city3"), WeatherList.class);
//        Weather weathernow3 = weather3.getWeathers().get(0);
//        Weather weatherdaily3 = weather3.getWeathers().get(7);
//        Weather weatherweekly3 = weather3.getWeathers().get(32);

//        Weather weatherweekly = gson.fromJson(getIntent().getStringExtra("weekly"), Weather.class);
        System.out.println(String.valueOf(weatherdaily1.getWeatherMain().getFeelsLike()));


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
        txtCity = findViewById(R.id.txtCity);

        btnHourly = findViewById(R.id.btn_hourly);
        btnDaily = findViewById(R.id.btn_daily);
        btnWeekly = findViewById(R.id.btn_weekly);
        btnLocation = findViewById(R.id.button_location);

        pageradapter = new ScreenSlideAdapter(this);
        viewpage.setAdapter(pageradapter);
        viewpage.setOffscreenPageLimit(3);

        SetTexthourly(weather1);



        viewpage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                txtTime.setText("Weather now");

                switch (position)
                {
                    case 0:
                        SetTexthourlySelected(weather1);
                        viewpager_position = 0;
                        txtCity.setText(weather1.getLocation().getCity());
                        break;
                    case 1:
                        SetTexthourlySelected(weather2);
                        viewpager_position = 1;
                        txtCity.setText(weather2.getLocation().getCity());
                        break;
                    case 2:
                        SetTexthourlySelected(weather3);
                        viewpager_position = 2;
                        txtCity.setText(weather3.getLocation().getCity());
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


        btnHourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (viewpager_position){
                    case 0:
                        SetTexthourly(weather1);
                        break;
                    case 1:
                        SetTexthourly(weather2);
                        break;
                    case 2:
                        SetTexthourly(weather3);
                        break;
                }
                txtTime.setText("Weather now");
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                txtTime.startAnimation(anim);
                ResetButtonColor();
                btnHourly.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });

        btnDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(viewpager_position);
                switch (viewpager_position){
                    case 0:
                        SetTextdaily(weather1);
                        break;
                    case 1:
                        SetTextdaily(weather2);
                        break;
                    case 2:
                        SetTextdaily(weather3);
                        break;
                }
                txtTime.setText("Weather tomorow");
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                txtTime.startAnimation(anim);
                ResetButtonColor();
                btnDaily.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });

        btnWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (viewpager_position){
                    case 0:
                        SetTextweekly(weather1);
                        break;
                    case 1:
                        SetTextweekly(weather2);
                        break;
                    case 2:
                        SetTextweekly(weather3);
                        break;
                }
                txtTime.setText("Weather this week");
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
                txtTime.startAnimation(anim);
                ResetButtonColor();
                btnWeekly.setTextColor(getResources().getColor(R.color.textcolor));
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpage.setCurrentItem(0, true);
            }
        });

    }

    private void SetTexthourly(WeatherList weatherList){
        Weather weather = weatherList.getWeathers().get(0);
        txtFeellike.setText(String.valueOf(Math.round(weather.getWeatherMain().getFeelsLike())) + " °C");
        txtHum.setText(String.valueOf(weather.getWeatherMain().getHumidity()) + " %");
        txtWindy.setText(String.valueOf(Math.round(weather.getWind().getSpeed() * 3.6)) + " km/h");
        txtPrecipation.setText(String.valueOf(weather.getWeatherMain().getPressure()) + " hPa");
    }

    private void SetTexthourlySelected(WeatherList weatherList){

        pre_feellike = Integer.parseInt(txtFeellike.getText().toString().split("°")[0].trim());
        pre_humidity = Integer.parseInt(txtHum.getText().toString().split("%")[0].trim());
        pre_windy = Integer.parseInt(txtWindy.getText().toString().split("km/h")[0].trim());
        pre_pressure = Integer.parseInt(txtPrecipation.getText().toString().split("hPa")[0].trim());

        Weather weather = weatherList.getWeathers().get(0);

        int feellike = (int) weather.getWeatherMain().getFeelsLike();
        startCountAnimationFell(txtFeellike, pre_feellike, feellike);

        int hummidity = (int) weather.getWeatherMain().getHumidity();
        startCountAnimationHum(txtHum, pre_humidity, hummidity);

        int windy = (int) weather.getWind().getSpeed();
        startCountAnimationWin(txtWindy, pre_windy, windy);

        int pressure = (int) weather.getWeatherMain().getPressure();
        startCountAnimationPre(txtPrecipation, pre_pressure, pressure);
//        txtFeellike.setText(String.valueOf(Math.round(weather.getWeatherMain().getFeelsLike())) + " °C");
//        txtHum.setText(String.valueOf(weather.getWeatherMain().getHumidity()) + " %");
//        txtWindy.setText(String.valueOf(Math.round(weather.getWind().getSpeed() * 3.6)) + " km/h");
//        txtPrecipation.setText(String.valueOf(weather.getWeatherMain().getPressure()) + " hPa");
    }

    private void startCountAnimationFell(TextView txtview, int first_temp, int second_temp) {
        ValueAnimator animator = ValueAnimator.ofInt(first_temp, second_temp);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                txtview.setText(animation.getAnimatedValue().toString() + "°");
            }
        });
        animator.start();
    }
    private void startCountAnimationHum(TextView txtview, int first_temp, int second_temp) {
        ValueAnimator animator = ValueAnimator.ofInt(first_temp, second_temp);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                txtview.setText(animation.getAnimatedValue().toString() + " %");
            }
        });
        animator.start();
    }
    private void startCountAnimationWin(TextView txtview, int first_temp, int second_temp) {
        ValueAnimator animator = ValueAnimator.ofInt(first_temp, second_temp);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                txtview.setText(animation.getAnimatedValue().toString() + " km/h");
            }
        });
        animator.start();
    }
    private void startCountAnimationPre(TextView txtview, int first_temp, int second_temp) {
        ValueAnimator animator = ValueAnimator.ofInt(first_temp, second_temp);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                txtview.setText(animation.getAnimatedValue().toString() + " hPa");
            }
        });
        animator.start();
    }

    private void SetTextdaily(WeatherList weatherList){
        Weather weather = weatherList.getWeathers().get(7);
        txtFeellike.setText(String.valueOf(Math.round(weather.getWeatherMain().getFeelsLike())) + " °C");
        txtHum.setText(String.valueOf(weather.getWeatherMain().getHumidity()) + " %");
        txtWindy.setText(String.valueOf(Math.round(weather.getWind().getSpeed() * 3.6)) + " km/h");
        txtPrecipation.setText(String.valueOf(weather.getWeatherMain().getPressure()) + " hPa");
    }
    private void SetTextweekly(WeatherList weatherList){
        Weather weather = weatherList.getWeathers().get(32);
        txtFeellike.setText(String.valueOf(Math.round(weather.getWeatherMain().getFeelsLike())) + " °C");
        txtHum.setText(String.valueOf(weather.getWeatherMain().getHumidity()) + " %");
        txtWindy.setText(String.valueOf(Math.round(weather.getWind().getSpeed() * 3.6)) + " km/h");
        txtPrecipation.setText(String.valueOf(weather.getWeatherMain().getPressure()) + " hPa");
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
            WeatherList weather1 = gson2.fromJson(getIntent().getStringExtra("city1"), WeatherList.class);
            Weather weathernow1 = weather1.getWeathers().get(0);


            WeatherList weather2 = gson2.fromJson(getIntent().getStringExtra("city2"), WeatherList.class);
            Weather weathernow2 = weather2.getWeathers().get(0);


            WeatherList weather3 = gson2.fromJson(getIntent().getStringExtra("city3"), WeatherList.class);
            Weather weathernow3 = weather3.getWeathers().get(0);

            switch (position){
                case 0:
                    return new ViewpagerScreen(weathernow1.getWeatherMain().getTemperature(), getPackageName(), R.raw.video1, weathernow1.getWeatherInfoList().get(0).getIcon(), weathernow1.getWeatherInfoList().get(0).getDescription(), weathernow1.getWeatherInfoList().get(0).getName());
                case 1:
                    return new ViewpagerScreen(weathernow2.getWeatherMain().getTemperature(), getPackageName(), R.raw.video2, weathernow2.getWeatherInfoList().get(0).getIcon(),weathernow2.getWeatherInfoList().get(0).getDescription(), weathernow2.getWeatherInfoList().get(0).getName());
                case 2:
                    return new ViewpagerScreen(weathernow3.getWeatherMain().getTemperature(), getPackageName(), R.raw.video3, weathernow3.getWeatherInfoList().get(0).getIcon(), weathernow3.getWeatherInfoList().get(0).getDescription(), weathernow3.getWeatherInfoList().get(0).getName());
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