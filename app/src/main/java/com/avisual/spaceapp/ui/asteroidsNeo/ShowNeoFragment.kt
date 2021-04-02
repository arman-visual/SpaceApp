package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.common.toast
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.databinding.ShowNeoFragmentBinding
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.repository.NeoRepository
import com.avisual.spaceapp.ui.asteroidsNeo.adapter.AsteroidsNeoAdapter
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.ShowNeoUi
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.ShowNeoViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.ShowNeoViewModelFactory
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class ShowNeoFragment : Fragment() {

    companion object {
        const val DATE_START_SEARCH_NEO = -2208952800000
        const val DATE_FINAL_SEARCH_NEO = 2556054000000
        const val TIME_ZONE = "UTC"
    }

    private lateinit var viewModel: ShowNeoViewModel
    private lateinit var binding: ShowNeoFragmentBinding
    private lateinit var neoRepository: NeoRepository
    private lateinit var adapter: AsteroidsNeoAdapter
    private lateinit var navController: NavController
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var outputDateFormat: SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dependencies()
        viewModel = buildViewModel()
        configureCalendar()
        setUpUi()
        subscribe()
        return binding.root
    }

    private fun setUpUi() {
        binding = ShowNeoFragmentBinding.inflate(layoutInflater)

        binding.showInput.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "DATA_PICKER")
            datePicker.addOnPositiveButtonClickListener { epochDate ->
                binding.showInput.text = giveFormatOutputDate(epochDate)
            }
        }
        binding.search.setOnClickListener {
            viewModel.getAsteroidsByOnlyDate(
                binding.showInput.text.toString(),
                getString(R.string.api_key)
            )
        }
        adapter = AsteroidsNeoAdapter(emptyList()) { onClickPhoto(it) }
        binding.recycler.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }

    private fun dependencies() {
        val database = Db.getDatabase(requireContext())
        neoRepository = NeoRepository(database)
    }

    private fun buildViewModel(): ShowNeoViewModel {
        val factory = ShowNeoViewModelFactory(neoRepository)
        return ViewModelProvider(this, factory).get(ShowNeoViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.listAsteroids.observe(requireActivity(), Observer(::updateUi))
    }

    private fun updateUi(model: ShowNeoUi) {
        binding.progressBarNeo.visibility =
            if (model is ShowNeoUi.Loading) View.VISIBLE else View.GONE

        if (model is ShowNeoUi.Content) {
            if (model.asteroids.isNotEmpty()) {
                adapter.setItems(model.asteroids)
            } else {
                adapter.setItems(model.asteroids)
                requireActivity().toast(getString(R.string.message_no_near))
            }
        }

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

    private fun giveFormatOutputDate(epochDate: Long): String {
        outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone(TIME_ZONE)
        }
        return outputDateFormat.format(epochDate)
    }

    private fun onClickPhoto(neo: Neo) {
        val action = ShowNeoFragmentDirections.actionShowNeoFragmentToDetailNeoFragment4(neo)
        findNavController().navigate(action)
    }
}