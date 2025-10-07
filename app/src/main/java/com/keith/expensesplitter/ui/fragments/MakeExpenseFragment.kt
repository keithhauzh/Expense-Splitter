package com.keith.expensesplitter.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.keith.expensesplitter.databinding.FragmentMakeExpenseBinding
import com.keith.expensesplitter.ui.adapters.ExpensesAdapter
import kotlinx.coroutines.launch
import com.keith.expensesplitter.R
import com.keith.expensesplitter.ui.view_models.ActivityViewModel
import com.keith.expensesplitter.ui.view_models.GroupCreationViewModel
import com.keith.expensesplitter.ui.view_models.MakeExpenseViewModel

class MakeExpenseFragment : Fragment() {
    private lateinit var binding: FragmentMakeExpenseBinding
    private val viewModel: MakeExpenseViewModel by viewModels()
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private lateinit var adapter: ExpensesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentMakeExpenseBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        error()
        setupClickListeners()
        observeExpensesCreation()
        adapter.makeExpense(ExpensesAdapter.ExpenseView())
    }

    private fun setupAdapter() {
        adapter = ExpensesAdapter {position ->
            adapter.removeExpense(position)
        }
        binding.rvExpenses.adapter = adapter
        binding.rvExpenses.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupClickListeners() {
        binding.mbAdd.setOnClickListener {
            adapter.makeExpense(ExpensesAdapter.ExpenseView())
        }
        binding.mbNext.setOnClickListener {
            val expenses = adapter.getExpenses().filter { expense ->
                expense.name.isNotBlank() && expense.amount > 0
            }
            if (expenses.isNotEmpty()) {
                savingExpenses(expenses)
            } else {
                atLeastOneExpense()
            }
        }
    }

    private fun savingExpenses(expenses: List<ExpensesAdapter.ExpenseView>) {
        lifecycleScope.launch {
            expenses.forEach { expenseView ->
                viewModel.makeExpense(expenseView.name, expenseView.amount)
            }
        }
    }

    private fun observeExpensesCreation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.expenses.collect { expenses ->
                expenses?.let {
                    activityViewModel.holdExpenses(it)
                    navigateToMakePeople()
                }
            }
        }
    }

    private fun navigateToMakePeople(){
        val action = MakeExpenseFragmentDirections
            .actionMakeExpenseFragmentToMakePersonFragment()
        findNavController().navigate(action)
    }

    private fun atLeastOneExpense() {
        val snackbar = Snackbar.make(
            binding.root,
            "Please put down at least one expense",
            Snackbar.LENGTH_LONG
        )
        snackbar.setBackgroundTint(
            ContextCompat.getColor(requireContext(), R.color.red)
        )
        snackbar.show()
    }

    private fun error() {
        lifecycleScope.launch {
            viewModel.error.collect {
                val snackbar = Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_LONG
                )
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.red)
                )
                snackbar.show()
            }
        }
    }
}