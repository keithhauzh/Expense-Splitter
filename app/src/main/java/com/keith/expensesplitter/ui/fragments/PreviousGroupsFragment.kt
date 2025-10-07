//package com.keith.expensesplitter.ui.fragments
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.keith.expensesplitter.databinding.FragmentPreviousGroupsBinding
//import com.keith.expensesplitter.ui.adapters.GroupsAdapter
//import kotlinx.coroutines.launch
//
//class PreviousGroupsFragment : Fragment() {
//    private lateinit var binding: FragmentPreviousGroupsBinding
//    private val viewModel:  PreviousGroupsViewModel by viewModels {
//        PreviousGroupsViewModel.Factory
//    }
//    private lateinit var adapter: GroupsAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        super.onCreate(savedInstanceState)
//        binding = FragmentPreviousGroupsBinding.inflate(
//            inflater,container,false
//        )
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupAdapter()
//        displayGroups()
//        binding.mbMake.setOnClickListener {
//            val action = PreviousGroupsFragmentDirections
//                .actionPreviousGroupsFragmentToMakeGroupFragment()
//            findNavController().navigate(action)
//        }
//        viewModel.getGroups()
//    }
//
//    fun setupAdapter() {
//        adapter = GroupsAdapter(
//            groups = emptyList(),
//            onClick = {
//                val action = PreviousGroupsFragmentDirections
//                    .actionPreviousGroupsFragmentToDisplayGroup(
//                        groupId = it.id!!
//                    )
//                findNavController().navigate(action)
//            } ,
//            viewModel = viewModel
//        )
//        binding.rvGroups.layoutManager =
//            LinearLayoutManager(requireContext())
//            binding.rvGroups.adapter = adapter
//    }
//
//    fun displayGroups() = lifecycleScope.launch {
//        viewModel.groups.collect {
//            adapter.setGroups(it)
//            binding.llEmpty.visibility =
//                if(it.isEmpty()) View.VISIBLE else
//                    View.GONE
//        }
//    }
//
//}