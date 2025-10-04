package com.keith.expensesplitter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.keith.expensesplitter.databinding.FragmentPreviousGroupsBinding

class PreviousGroupsFragment : Fragment() {
    private lateinit var binding: FragmentPreviousGroupsBinding
    private val viewModel:  PreviousGroupsViewModel by viewModels {
        PreviousGroupsViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentPreviousGroupsBinding.inflate(
            inflater,container,false
        )
        return binding.root
    }

}