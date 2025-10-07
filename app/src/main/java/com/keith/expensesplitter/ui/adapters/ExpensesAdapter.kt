package com.keith.expensesplitter.ui.adapters
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.keith.expensesplitter.R
import java.text.DecimalFormat

class ExpensesAdapter (
    private val onRemoveClick: (Int) -> Unit
): RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder>() {
    private val expenses = mutableListOf<ExpenseView>()
    private val decimalFormat = DecimalFormat("#.##")

    inner class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val etName : EditText = itemView.findViewById(R.id.etName)
        val etAmount : EditText = itemView.findViewById(R.id.etAmount)
        val ivRemove : ImageView = itemView.findViewById(R.id.ivRemove)

        fun bind(expense: ExpenseView, position: Int) {
            etName.setText(expense.name)
            etAmount.setText(decimalFormat.format(expense.amount).toString())

            etName.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    expenses[position].name = s.toString()
                }
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }
            )

            etAmount.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    expenses[position].amount = s.toString().toFloatOrNull()?:0.0f
                }
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }
            )

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

    fun makeExpense(expense: ExpenseView) {
        expenses.add(expense)
        notifyItemInserted(expenses.size - 1)
    }

    fun removeExpense(position: Int) {
        if (expenses.size > 1) {
            expenses.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getExpenses(): List<ExpenseView>  {
        return expenses.map { ExpenseView(it.name, it.amount) }
    }

    data class ExpenseView (
        var name: String = "",
        var amount: Float = 0.0f
    )
}