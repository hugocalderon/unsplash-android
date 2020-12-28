package com.example.myapplication;

import android.app.Application;

import com.unsplash.pickerandroid.photopicker.UnsplashPhotoPicker;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Parse SDK stuff goes here
      /*  UnsplashPhotoPicker.init(
                this,
                "k7ulZqGku77Lhzc0YavFYRR4VhI7VmeBM8-sdeA5azY",
                "y8FHeMPIGFKxZmLJlDfH7b7qmco3gfl94PWygafy2Rk"
                // optional page size (number of photos per page) 
        ); */
    }
}
