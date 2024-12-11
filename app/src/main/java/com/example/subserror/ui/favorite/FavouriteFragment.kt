package com.example.subserror.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subserror.R
import com.example.subserror.databinding.FragmentFavouriteBinding
import com.example.subserror.ui.EventViewModel
import com.example.subserror.ui.ViewModelFactory
import com.example.subserror.ui.finished.FavouriteAdapter


class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding

    private lateinit var favouriteAdapter: FavouriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteAdapter = FavouriteAdapter()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }

        val layoutManager = LinearLayoutManager(requireContext())
        _binding?.rvFavouriteEvent?.layoutManager = layoutManager
        _binding?.rvFavouriteEvent?.adapter = favouriteAdapter

        viewModel.getFavouriteEvent().observe(viewLifecycleOwner) { event ->
            favouriteAdapter.submitList(event)
        }
    }

}