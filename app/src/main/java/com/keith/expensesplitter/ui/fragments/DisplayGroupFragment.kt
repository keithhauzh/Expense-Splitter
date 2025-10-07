package com.keith.expensesplitter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.keith.expensesplitter.databinding.FragmentDisplayGroupBinding
import com.keith.expensesplitter.ui.adapters.ExpensesListAdapter
import com.keith.expensesplitter.ui.adapters.PeopleListAdapter
import com.keith.expensesplitter.ui.view_models.DisplayGroupViewModel
import kotlinx.coroutines.launch

class DisplayGroupFragment : Fragment() {
    private val viewModel: DisplayGroupViewModel by viewModels {
        DisplayGroupViewModel.Factory
    }
    private lateinit var binding: FragmentDisplayGroupBinding
    private lateinit var expensesListAdapter: ExpensesListAdapter
    private lateinit var peopleListAdapter: PeopleListAdapter
    private val args: DisplayGroupFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisplayGroupBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = args.groupId
        super.onViewCreated(view, savedInstanceState)
        expensesListAdapter = ExpensesListAdapter(emptyList())
        peopleListAdapter = PeopleListAdapter(emptyList())

        setupExpensesAdapter()
        setupPeopleAdapter()
        viewModel.calculateExpenses(id)
        viewModel.loadGroup(id)
        viewModel.loadExpenses(id)
        viewModel.loadPeople(id)

        getTotalAmount()
        getNumberOfPeople()
        getAmountPerPerson()
        setupObservers()
    }

    private fun setupObservers(){
        fetchGroup()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.expenses.collect { expenses ->
                expenses.let { expensesList ->
                    expensesListAdapter.setExpenses(expensesList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.people.collect { people ->
                people.let { peopleList ->
                    peopleListAdapter.setPeople(peopleList)
                }
            }
        }
    }

    private fun fetchGroup(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.group.collect { group ->
                group?.let {
                    binding.run {
                        groupName.text = group.name
                        groupDetails.text = group.details
                    }
                }
            }
        }
    }

    private fun setupExpensesAdapter(){
        binding.rvExpenses.layoutManager = LinearLayoutManager(
            requireContext()
        )
        binding.rvExpenses.adapter = expensesListAdapter
    }

    private fun setupPeopleAdapter(){
        binding.rvPeople.layoutManager = LinearLayoutManager(
            requireContext()
        )
        binding.rvPeople.adapter = peopleListAdapter
    }

    private fun getTotalAmount(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalAmount.collect { amount ->
                binding.totalExpenses.text = String.format(
                    "$%.2f", amount
                )
            }
        }
    }

    private fun getNumberOfPeople(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.numberOfPeople.collect { number ->
                binding.tvNumberOfPeople.text = String.format(
                    "${number}"
                )
            }
        }
    }

    private fun getAmountPerPerson(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.amountPerPerson.collect { amount ->
                binding.eachPerson.text = String.format(
                    "$${amount}"
                )
            }
        }
    }
}