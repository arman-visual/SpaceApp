package com.avisual.spaceapp.ui.mainMenu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ActivityMainBinding
import com.avisual.spaceapp.ui.exploreGallery.NasaGalleryActivity
import com.avisual.spaceapp.ui.asteroidsNeo.AsteroidsNeoActivity
import com.avisual.spaceapp.ui.roverMars.RoverMarsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.imageView.setImageResource(R.drawable.spacemenu)
        setContentView(binding.root)
        binding.btNeows.setOnClickListener { menuNav(Intent(this, AsteroidsNeoActivity::class.java)) }
        binding.btNasagallery.setOnClickListener { menuNav(Intent(this, NasaGalleryActivity::class.java)) }
        binding.btSearchrover.setOnClickListener { menuNav(Intent(this, RoverMarsActivity::class.java)) }
    }

    private fun menuNav(intent: Intent) {
        startActivity(intent)
    }
}