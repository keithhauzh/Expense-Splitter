package com.keith.expensesplitter.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.keith.expensesplitter.data.model.Expense
import com.keith.expensesplitter.databinding.FragmentMakeExpenseBinding
import com.keith.expensesplitter.ui.adapter.ExpensesAdapter
import com.keith.expensesplitter.ui.view_models.MakeExpenseViewModel
import com.keith.expensesplitter.ui.view_models.MakeGroupViewModel
import kotlinx.coroutines.launch

class MakeExpenseFragment : Fragment() {
    private lateinit var binding: FragmentMakeExpenseBinding
    private val args: MakeExpenseFragmentArgs by navArgs()
    private val groupId: Int get() = args.groupId
    private val viewModel: MakeExpenseViewModel by viewModels {
        MakeExpenseViewModel.Factory
    }
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

        binding.mbAdd.setOnClickListener {
            adapter.addExpense(ExpensesAdapter.ExpenseView())
        }

        adapter.addExpense(ExpensesAdapter.ExpenseView())

        binding.mbNext.setOnClickListener {
            val expenses = adapter.getExpenses().filter { expense ->
                expense.name.isNotBlank() && expense.amount > 0
            }
            if(expenses.isNotEmpty()) {
                lifecycleScope.launch {
                    expenses.forEach { expenseView ->
                        val expense = Expense(
                            name = expenseView.name,
                            amount = expenseView.amount,
                            groupId = groupId
                        )
                        viewModel.
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = ExpensesAdapter {position ->
            adapter.removeExpense(position)
        }

        binding.rvExpenses.adapter = adapter
        binding.rvExpenses.layoutManager = LinearLayoutManager(requireContext())
    }

}