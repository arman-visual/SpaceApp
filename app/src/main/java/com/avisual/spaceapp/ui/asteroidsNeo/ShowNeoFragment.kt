package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ShowNeoFragmentBinding
import com.avisual.spaceapp.repository.NeoRepository
import com.avisual.spaceapp.ui.asteroidsNeo.adapter.AsteroidsNeoAdapter
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.ShowNeoViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.ShowNeoViewModelFactory
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class ShowNeoFragment : Fragment() {

    private lateinit var viewModel: ShowNeoViewModel
    private lateinit var binding: ShowNeoFragmentBinding
    private lateinit var neoRepository: NeoRepository
    private lateinit var adapter: AsteroidsNeoAdapter
    private lateinit var datePicker: MaterialDatePicker<Long>

    private val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    companion object {
        const val DATE_START_SEARCH_NEO = -2208952800000
        const val DATE_FINAL_SEARCH_NEO = 2556054000000
        const val TIME_ZONE = "UTC"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dependencies()
        viewModel = buildViewModel()
        configureCalendar()
        binding = ShowNeoFragmentBinding.inflate(layoutInflater)

        binding.buttonCalendar.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "DATA_PICKER")
            datePicker.addOnPositiveButtonClickListener {
                val text = outputDateFormat.format(it)
                Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun configureCalendar() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE))

        calendar.timeInMillis = DATE_START_SEARCH_NEO
        calendar[Calendar.JANUARY] = Calendar.JANUARY
        val startYear = calendar.timeInMillis

        calendar.timeInMillis = DATE_FINAL_SEARCH_NEO
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAsteroidsByDate("2021-02-21", "2021-02-26", getString(R.string.api_key))
        adapter = AsteroidsNeoAdapter(emptyList())
        binding.recycler.adapter = adapter
        subscribe()
    }

    private fun subscribe() {
        viewModel.listAsteroids.observe(requireActivity()) {
            adapter.setItems(it)
        }
    }

    private fun dependencies() {
        neoRepository = NeoRepository()
    }

    private fun buildViewModel(): ShowNeoViewModel {
        val factory = ShowNeoViewModelFactory(neoRepository)
        return ViewModelProvider(this, factory).get(ShowNeoViewModel::class.java)
    }


}