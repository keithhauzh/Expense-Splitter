package com.keith.expensesplitter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.keith.expensesplitter.R

class ExpensesAdapter (
    private val onRemoveClick: (Int) -> Unit
): RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder>() {
    private val expenses = mutableListOf<ExpenseView>()

    inner class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val etName : TextInputEditText = itemView.findViewById(R.id.etName)
        val etAmount : TextInputEditText = itemView.findViewById(R.id.etAmount)
        val ivRemove : ImageView = itemView.findViewById(R.id.ivRemove)

        fun bind(expense: ExpenseView, position: Int) {
            etName.setText(expense.name)
            etAmount.setText(expense.amount.toString())
            ivRemove.setOnClickListener {
                onRemoveClick(position)
            }
        }
    }

    override fun onCreateViewHolder
                (parent: ViewGroup, viewType: Int)
    : ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder
                (holder: ExpenseViewHolder, position: Int)
    {
        holder.bind(expenses[position], position)
    }

    override fun getItemCount() = expenses.size

    fun addExpense(expense: ExpenseView) {
        expenses.add(expense)
        notifyItemInserted(expenses.size - 1)
    }

    fun removeExpense(position: Int) {
        if (expenses.size > 1) {
            expenses.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getExpenses(): List<ExpenseView> = expenses.toList()

    data class ExpenseView (
        var name: String = "",
        var amount: Float = 0.0f
    )
}