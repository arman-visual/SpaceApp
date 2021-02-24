package com.avisual.spaceapp.ui.exploreGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avisual.spaceapp.databinding.FragmentExploreGalleryBinding

class ExploreGalleryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentExploreGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

}