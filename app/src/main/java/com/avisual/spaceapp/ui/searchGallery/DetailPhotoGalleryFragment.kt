package com.avisual.spaceapp.ui.searchGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avisual.spaceapp.databinding.FragmentDetailPhotoGalleryBinding


class DetailPhotoGalleryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailPhotoGalleryBinding.inflate(layoutInflater)
        binding.textExample.text = "Acceso Test"
        return binding.root
    }

}