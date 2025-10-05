package com.keith.expensesplitter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keith.expensesplitter.data.model.Expense
import com.keith.expensesplitter.databinding.ItemLayoutListExpenseBinding

class ExpensesListAdapter(
    private var expenses: List<Expense>
): RecyclerView.Adapter<ExpensesListAdapter.ExpenseViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): ExpenseViewHolder  {
        val binding = ItemLayoutListExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ExpenseViewHolder,
                                  position: Int) {
        val expense = expenses[position]
        holder.bind(expense)
    }

    fun setExpenses(expenses: List<Expense>) {
        this.expenses = expenses
        notifyDataSetChanged()
    }

    override fun getItemCount() = expenses.size

    inner class ExpenseViewHolder(
        private val binding: ItemLayoutListExpenseBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: Expense){
            binding.run {
                tvExpenseItemName.text = expense.name
                tvExpenseItemAmount.text = expense.amount.toString()
            }
        }
    }
}