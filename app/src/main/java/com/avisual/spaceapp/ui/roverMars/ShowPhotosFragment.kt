package com.avisual.spaceapp.ui.roverMars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.avisual.data.repository.RoverRepository
import com.avisual.spaceapp.R
import com.avisual.spaceapp.ui.common.toast
import com.avisual.spaceapp.databinding.FragmentShowPhotosBinding
import com.avisual.spaceapp.data.model.PhotoRover
import com.avisual.spaceapp.data.server.ServerRoverDataSource
import com.avisual.spaceapp.ui.roverMars.adapter.PhotosRoverAdapter
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosUi
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosViewModel
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosViewModelFactory
import com.avisual.usecases.GetRoverPhotosByDate
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class ShowPhotosFragment : Fragment() {

    companion object {
        const val DATE_START_SEARCH_NEO = 1344272400000
        const val TIME_ZONE = "UTC"
    }

    private lateinit var binding: FragmentShowPhotosBinding
    private lateinit var adapter: PhotosRoverAdapter
    private lateinit var viewModel: ShowPhotosViewModel
    private lateinit var roverRepository: RoverRepository
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var outputDateFormat: SimpleDateFormat
    private lateinit var getRoverPhotosByDate: GetRoverPhotosByDate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buildDependencies()
        viewModel = buildViewModel()
        configureCalendar()
        setupUi()
        subscribeUi()
        return binding.root
    }

    private fun buildDependencies() {
        val remote = ServerRoverDataSource()
        val apiKey = getString(R.string.api_key)
        roverRepository = RoverRepository(remote, apiKey)
        getRoverPhotosByDate = GetRoverPhotosByDate(roverRepository)
    }

    private fun buildViewModel(): ShowPhotosViewModel {
        val factory = ShowPhotosViewModelFactory(getRoverPhotosByDate)
        return ViewModelProvider(this, factory).get(ShowPhotosViewModel::class.java)
    }

    private fun setupUi() {
        binding = FragmentShowPhotosBinding.inflate(layoutInflater)
        adapter = PhotosRoverAdapter(emptyList()) {
            onClickPhoto(it)
        }
        binding.recycler.adapter = adapter

        binding.showInput.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "DATA_PICKER")
            datePicker.addOnPositiveButtonClickListener { epochDate ->
                binding.showInput.text = giveFormatOutputDate(epochDate)
            }
        }

        binding.search.setOnClickListener { onClickSearchButton() }

    }

    private fun subscribeUi() {
        viewModel.model.observe(requireActivity(), Observer(::updateUi))
    }

    private fun updateUi(model: ShowPhotosUi) {
        binding.progressBarM.visibility =
            if (model is ShowPhotosUi.Loading) View.VISIBLE else View.GONE

        if (model is ShowPhotosUi.Content) {
            if (model.photos.isNotEmpty()) {
                adapter.setItems(model.photos)
            } else {
                adapter.setItems(model.photos)
                requireActivity().toast(getString(R.string.message_no_photos))
            }
        }
    }

    private fun configureCalendar() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE))
        val today = MaterialDatePicker.todayInUtcMilliseconds()

        calendar.timeInMillis = DATE_START_SEARCH_NEO
        calendar[Calendar.JANUARY] = Calendar.JANUARY
        val startYear = calendar.timeInMillis

        calendar.timeInMillis = today
        calendar[Calendar.DECEMBER] = Calendar.DECEMBER
        val finalYear = calendar.timeInMillis

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setStart(startYear)
                .setEnd(finalYear)

        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintsBuilder.build())
            .build()
    }

    private fun giveFormatOutputDate(epochDate: Long): String {
        outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone(TIME_ZONE)
        }
        return outputDateFormat.format(epochDate)
    }

    private fun onClickSearchButton() {
        viewModel.findPhotosByDate(
            binding.showInput.text.toString()
        )
    }

    private fun onClickPhoto(photo: PhotoRover) {
        val action =
            ShowPhotosFragmentDirections.actionShowPhotosFragmentToDetailPhotoRoverFragment(photo)
        findNavController().navigate(action)
    }

}