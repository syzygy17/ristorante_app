package com.ristorante.ristoranteapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}