package com.example.myapplication

import android.app.Application
import com.unsplash.pickerandroid.photopicker.UnsplashPhotoPicker

class Global : Application() {

    override fun onCreate() {
        super.onCreate()
        // initializing the picker library
        UnsplashPhotoPicker.init(
                this,
                "k7ulZqGku77Lhzc0YavFYRR4VhI7VmeBM8-sdeA5azY",
                "y8FHeMPIGFKxZmLJlDfH7b7qmco3gfl94PWygafy2Rk"
                /* optional page size (number of photos per page) */
        )
        /* .setLoggingEnabled(true) // if you want to see the http requests */
    }
}