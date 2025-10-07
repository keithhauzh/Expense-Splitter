package com.keith.expensesplitter.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.keith.expensesplitter.databinding.FragmentMakePersonBinding
import com.keith.expensesplitter.ui.adapters.PeopleAdapter
import com.keith.expensesplitter.R
import com.keith.expensesplitter.data.model.Person
import com.keith.expensesplitter.ui.view_models.ActivityViewModel
import com.keith.expensesplitter.ui.view_models.MakePersonViewModel
import kotlinx.coroutines.launch

class MakePersonFragment: Fragment() {
    private lateinit var binding: FragmentMakePersonBinding
    private val viewModel: MakePersonViewModel by viewModels()
    private val activityViewModel: ActivityViewModel by activityViewModels{
        ActivityViewModel.Factory
    }

    private lateinit var adapter: PeopleAdapter
    private var hasSavedPeople = false

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
        viewModel.resetPeople()
        hasSavedPeople = false
        setupAdapter()
        error()
        setupClickListeners()
        observePeopleCreation()
        adapter.makePerson(PeopleAdapter.PersonView())
    }

    fun setupAdapter() {
        adapter = PeopleAdapter {position ->
            adapter.removePerson(position)
        }
        binding.rvPeople.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPeople.adapter = adapter
    }

    fun setupClickListeners() {
        binding.mbAdd.setOnClickListener {
            adapter.makePerson(PeopleAdapter.PersonView())
        }
        binding.mbNext.setOnClickListener {
            val people = adapter.getPeople().filter { person ->
                person.name.isNotBlank()
            }
            if(people.isNotEmpty()) {
                if(!hasSavedPeople){
                    savingPeople(people)
                    hasSavedPeople = true
                }
            } else {
                ifPeopleEmpty()
            }
        }
    }
    private fun savingPeople(people: List<PeopleAdapter.PersonView>){
        lifecycleScope.launch {
            people.forEach { personView ->
                viewModel.makePerson(personView.name)
            }
        }
    }

    private fun observePeopleCreation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.people.collect { people ->
                people.let {
                    if(it.isNotEmpty()){
                        activityViewModel.holdPeople(it)
                        finish()
                    }
                }
            }
        }
    }

    private fun ifPeopleEmpty(){
        val snackbar = Snackbar.make(
            binding.root,
            "Please put down at least one person",
            Snackbar.LENGTH_LONG
        )
        snackbar.setBackgroundTint(
            ContextCompat.getColor(requireContext(), R.color.red)
        )
        snackbar.show()
    }

    private fun finish() {
        activityViewModel.complete()
        findNavController().popBackStack(
            R.id.homeFragment,
            false
        )
        viewModel.resetPeople()
        success()
    }

    private fun success() {
        val snackbar = Snackbar.make(
            binding.root,
            "Expense Group has been made successfully :)",
            Snackbar.LENGTH_LONG
        )
        snackbar.setBackgroundTint(
            ContextCompat.getColor(requireContext(), R.color.green)
        )
        snackbar.show()
    }

    private fun error() {
        lifecycleScope.launch {
            viewModel.error.collect { error ->
                if(error.isNotBlank()){
                    showError(error)
                    hasSavedPeople=false
                }
            }
        }
    }

    private fun showError(error: String){
        val snackbar = Snackbar.make(
            binding.root,
            error,
            Snackbar.LENGTH_LONG
        )
        snackbar.setBackgroundTint(
            ContextCompat.getColor(requireContext(), R.color.red)
        )
        snackbar.show()
    }
}