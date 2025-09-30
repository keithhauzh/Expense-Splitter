package com.keith.expensesplitter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keith.expensesplitter.databinding.FragmentMakePersonBinding
import com.keith.expensesplitter.ui.adapter.PeopleAdapter
import com.keith.expensesplitter.R
import com.keith.expensesplitter.ui.view_models.MakeGroupViewModel

class MakePersonFragment: Fragment() {
    private lateinit var binding: FragmentMakePersonBinding

    private val viewModel: MakeGroupViewModel by viewModels {
        MakeGroupViewModel.Factory
    }

    private lateinit var adapter: PeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentMakePersonBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
    }

    fun setupAdapter() {
        adapter = PeopleAdapter(emptyList()) {
            val action = MakePersonFragmentDirections.actionMakePersonFragmentToHomeFragment()
            findNavController().navigate(action)
        }
        binding.rvPeople.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPeople.adapter = adapter
    }

}