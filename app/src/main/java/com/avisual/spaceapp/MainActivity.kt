package com.avisual.spaceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avisual.spaceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.imageView.setImageResource(R.drawable.spacemenu)
        setContentView(binding.root)
    }
}