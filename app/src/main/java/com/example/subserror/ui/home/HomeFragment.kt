package com.example.subserror.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subserror.data.Result
import com.example.subserror.data.remote.response.ListEventsItem
import com.example.subserror.databinding.FragmentHomeBinding
import com.example.subserror.ui.EventViewModel
import com.example.subserror.ui.ViewModelFactory
import com.example.subserror.ui.setting.SettingPreferences
import com.example.subserror.ui.setting.SettingViewModel
import com.example.subserror.ui.setting.SettingViewModelFactory
import com.example.subserror.ui.setting.dataStore

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val upcomingEventAdapter = UpcomingEventAdapter()
    private val finishedEventAdapter = FinishedEventAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUpcomingEventHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = upcomingEventAdapter
        }

        binding.rvFinishedEventHome.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = finishedEventAdapter
        }

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }


        viewModel.activeEvents.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoadingUpcoming(true)
                is Result.Success -> {
                    showLoadingUpcoming(false)
                    upcomingEventAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    showLoadingUpcoming(false)
                    Toast.makeText(context, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.inactiveEvents.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoadingFinished(true)
                is Result.Success -> {
                    showLoadingFinished(false)
                    finishedEventAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    showLoadingFinished(false)
                    Toast.makeText(context, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoadingUpcoming(isLoading: Boolean) {
        binding.progressBarUpcoming.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingFinished(isLoading: Boolean) {
        binding.progressBarFinished.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}