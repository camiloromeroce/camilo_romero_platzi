package com.example.weather.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToMainActivity()
    }

    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}