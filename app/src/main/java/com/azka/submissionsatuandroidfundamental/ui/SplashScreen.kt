package com.azka.submissionsatuandroidfundamental.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.azka.submissionsatuandroidfundamental.R
import com.azka.submissionsatuandroidfundamental.ui.viewmodel.MainViewModel
import com.azka.submissionsatuandroidfundamental.ui.viewmodel.ViewModelFactory

class SplashScreen:AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val splashScreenDuration = 2000L
        Thread(Runnable {
            Thread.sleep(splashScreenDuration)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }).start()

    }
}