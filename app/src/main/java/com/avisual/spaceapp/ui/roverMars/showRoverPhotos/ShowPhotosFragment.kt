package com.avisual.spaceapp.ui.roverMars.showRoverPhotos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.model.PhotoRover
import com.avisual.spaceapp.databinding.FragmentShowPhotosBinding
import com.avisual.spaceapp.ui.common.toast
import com.avisual.spaceapp.ui.roverMars.adapter.PhotosRoverAdapter
import com.avisual.spaceapp.ui.roverMars.showRoverPhotos.viewModel.ShowPhotosViewModel
import com.avisual.spaceapp.ui.roverMars.showRoverPhotos.viewModel.ShowPhotosViewModel.ShowPhotosUi
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ShowPhotosFragment : Fragment() {

    companion object {
        const val DATE_START_SEARCH_NEO = 1344272400000
        const val TIME_ZONE = "UTC"
    }

    private var _binding: FragmentShowPhotosBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PhotosRoverAdapter
    private val viewModel: ShowPhotosViewModel by viewModel()
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var outputDateFormat: SimpleDateFormat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowPhotosBinding.inflate(inflater, container, false)
        configureCalendar()
        setupUi()
        subscribeUi()
        return binding.root
    }

    private fun setupUi() {
        adapter = PhotosRoverAdapter(emptyList()) {
            onClickPhoto(it)
        }
        binding.recycler.adapter = adapter

        binding.showInput.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "DATA_PICKER")
        }

        binding.search.setOnClickListener { onClickSearchButton() }

    }

    private fun subscribeUi() {
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(model: ShowPhotosUi) {
        binding.progressBarM.visibility =
            if (model is ShowPhotosUi.Loading) View.VISIBLE else View.GONE

        if (model is ShowPhotosUi.Content) {
            if (model.photos.isNotEmpty()) {
                adapter.setItems(model.photos)
            } else {
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
            .build().apply {
                addOnPositiveButtonClickListener { epochDate ->
                    binding.showInput.text = giveFormatOutputDate(epochDate)
                }
            }
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}