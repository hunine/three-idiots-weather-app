package com.threeidiots.myapplication.view;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.threeidiots.myapplication.R;
import com.threeidiots.myapplication.model.Weather;
import com.threeidiots.myapplication.model.WeatherList;
import com.threeidiots.myapplication.viewmodel.WeatherApiService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {

    private WeatherApiService apiService;
    private static final int REQUEST_CHECK_SETTING = 100;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final String TAG = SplashScreen.class.getSimpleName();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private boolean requestingLocationUpdates = false;
    double latitude = 0.0;
    double longitude = 0.0;
    private int count =0;
    private WeatherList list1;
    private WeatherList list2;
    private WeatherList list3;

    private ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();


        imgview = findViewById(R.id.imageview);
        Glide.with(this).load(getDrawable(R.drawable.cat2o_agadigadka9qfa)).into(imgview);

        //      API
        apiService = new WeatherApiService();

//      Get current coordinates
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                currentLocation = locationResult.getLastLocation();

                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();


                Log.d("Latitude", Double.toString(latitude));
                Log.d("longitude", Double.toString(longitude));

                apiService.getWeatherList("Hanoi")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<WeatherList>() {
                            @Override
                            public void onSuccess(@NonNull WeatherList weatherList) {
                                list2 = weatherList;

                                openNextActivity();

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("DEBUG1", "Fail " + e.getMessage());

                            }
                        });
                apiService.getWeatherList("Dalat")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<WeatherList>() {
                            @Override
                            public void onSuccess(@NonNull WeatherList weatherList) {
                                Log.d("DEBUG1", "Success");
                                list3 = weatherList;

                                openNextActivity();

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("DEBUG1", "Fail " + e.getMessage());

                            }
                        });



                apiService.getWeatherList(latitude, longitude)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<WeatherList>() {
                            @Override
                            public void onSuccess(@NonNull WeatherList weatherList) {
                                Log.d("DEBUG1", "Success");
                                list1 = weatherList;

                                for (Weather weather: weatherList.getWeathers()) {
                                    Log.d("SPACE", "----------------------------------------");
                                    Log.d("Datetime Forecasted", weather.getDateTimeForecasted());
                                    Log.d("DT", Integer.toString(weather.getDt()));
                                    Log.d("Temperature", Double.toString(weather.getWeatherMain().getTemperature()));
                                    Log.d("Feels like", Double.toString(weather.getWeatherMain().getFeelsLike()));
                                    Log.d("Pressure", Double.toString(weather.getWeatherMain().getPressure()));
                                    Log.d("Humidity", Integer.toString(weather.getWeatherMain().getHumidity()));
                                    Log.d("Weather name", weather.getWeatherInfoList().get(0).getName());
                                    Log.d("Description", weather.getWeatherInfoList().get(0).getDescription());
                                    Log.d("Wind speed", Double.toString(weather.getWind().getSpeed()));
                                    Log.d("Icon", weather.getWeatherInfoList().get(0).getIcon());

                                }

                                openNextActivity();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("DEBUG1", "Fail " + e.getMessage());

                            }
                        });
            }
        };

        locationRequest = LocationRequest.create()
                .setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        requestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void openNextActivity(){
        count +=1;
//        System.out.println(String.valueOf(count) + "count nè");
        if(count >2){
            Gson gson = new Gson();
            String weather1 = gson.toJson(list1);
            String weather2 = gson.toJson(list2);
            String weather3 = gson.toJson(list3);

            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            i.putExtra("city1",weather1);
            i.putExtra("city2",weather2);
            i.putExtra("city3",weather3);
            startActivity(i);
        }
    }


    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startLocationUpdates() {
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes
                                    .RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attemping to upgrade location settings");

                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(SplashScreen.this, REQUEST_CHECK_SETTING);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be fixed here. Fix in settings";
                                Log.i(TAG, errorMessage);

                                Toast.makeText(SplashScreen.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback).addOnCompleteListener(this, task -> Log.d(TAG, "Location updates stopped!"));
    }

    private boolean checkPermission() {
        int permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdates && checkPermission()) {
            startLocationUpdates();
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (requestingLocationUpdates) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }


}