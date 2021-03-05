package com.avisual.spaceapp.ui.searchGallery

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ActivityNavGalleryBinding

class NavGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("NasaGalleryActivity", "Init this activity")
    }
}