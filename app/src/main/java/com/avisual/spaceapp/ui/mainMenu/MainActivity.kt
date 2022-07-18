package com.avisual.spaceapp.ui.mainMenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avisual.spaceapp.databinding.ActivityMainBinding
import com.avisual.spaceapp.ui.asteroidsNeo.AsteroidsNeoActivity
import com.avisual.spaceapp.ui.common.startActivity
import com.avisual.spaceapp.ui.common.toast
import com.avisual.spaceapp.ui.gallery.GalleryActivity
import com.avisual.spaceapp.ui.roverMars.NavRoverMarsActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.btNeows.setOnClickListener { startActivity<AsteroidsNeoActivity>() }
        binding.btNasagallery.setOnClickListener { startActivity<GalleryActivity>() }
        binding.btSearchrover.setOnClickListener { startActivity<NavRoverMarsActivity>() }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                toast("Estas en: $location")
            }
        }
    }
}
