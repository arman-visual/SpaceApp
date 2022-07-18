package com.avisual.spaceapp.ui.asteroidsNeo.showNeos

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.databinding.ShowNeoFragmentBinding
import com.avisual.spaceapp.ui.asteroidsNeo.adapter.AsteroidsNeoAdapter
import com.avisual.spaceapp.ui.asteroidsNeo.showNeos.ShowNeoViewModel.ShowNeoUi
import com.avisual.spaceapp.ui.common.toast
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ShowNeoFragment : ScopeFragment() {

    companion object {
        const val DATE_START_SEARCH_NEO = -2208952800000
        const val DATE_FINAL_SEARCH_NEO = 2556054000000
        const val TIME_ZONE = "UTC"
    }

    private val viewModel: ShowNeoViewModel by viewModel()
    private var _binding: ShowNeoFragmentBinding? = null
    private val binding get() = _binding!!
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
        _binding = ShowNeoFragmentBinding.inflate(inflater, container, false)
        subscribe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureCalendar()
        setUpUi()
        navController = view.findNavController()
    }

    private fun setUpUi() {
        binding.showInput.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, "DATA_PICKER")
        }
        binding.search.setOnClickListener {//TODO aquispe controlar consultas sin conexion
            val cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo!=null && cm.activeNetworkInfo!!.isAvailable)
                viewModel.getAsteroidsByOnlyDate(
                    binding.showInput.text.toString()
                )
            else
                requireActivity().toast("No hay internet")
        }
        adapter = AsteroidsNeoAdapter(emptyList()) { onClickPhoto(it) }
        binding.recycler.adapter = adapter
    }

    private fun subscribe() {
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(model: ShowNeoUi) {

        binding.progressBarNeo.visibility =
            if (model is ShowNeoUi.Loading) View.VISIBLE else View.GONE

        if (model is ShowNeoUi.Content) {
            if (model.asteroids.isNotEmpty()) {
                adapter.setItems(model.asteroids)
            } else {
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

    private fun onClickPhoto(neo: Neo) {
        val action = ShowNeoFragmentDirections.actionShowNeoFragmentToDetailNeoFragment4(neo)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}