package com.avisual.spaceapp.ui.roverMars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.databinding.FragmentSavedPhotosBinding
import com.avisual.spaceapp.repository.PhotoCuriosityRepository
import com.avisual.spaceapp.ui.roverMars.adapter.PhotosRoverAdapter
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosViewModel
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosViewModelFactory

class ShowPhotosFragment : Fragment() {

    private lateinit var binding: FragmentSavedPhotosBinding
    private lateinit var adapter: PhotosRoverAdapter
    private lateinit var viewModel: ShowPhotosViewModel
    private lateinit var photoCuriosityRepository: PhotoCuriosityRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buildDependencies()
        viewModel = buildViewModel()
        binding = FragmentSavedPhotosBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PhotosRoverAdapter(emptyList())
        binding.recycler.adapter = adapter
        subscribeUi()
    }

    private fun buildDependencies() {
        photoCuriosityRepository = PhotoCuriosityRepository()
    }

    private fun buildViewModel(): ShowPhotosViewModel {
        val factory = ShowPhotosViewModelFactory(photoCuriosityRepository)
        return ViewModelProvider(this, factory).get(ShowPhotosViewModel::class.java)
    }

    private fun subscribeUi() {
        viewModel.photosCuriosity.observe(requireActivity()) {
            adapter.setItems(it)
        }
    }
}