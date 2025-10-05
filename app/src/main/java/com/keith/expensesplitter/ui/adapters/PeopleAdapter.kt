package com.keith.expensesplitter.ui.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.keith.expensesplitter.R

class PeopleAdapter(
    private val onRemoveClick: (Int) -> Unit
): RecyclerView.Adapter<PeopleAdapter.PersonViewHolder>() {

    private val people = mutableListOf<PersonView>()

    inner class PersonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val etName: EditText = itemView.findViewById(R.id.etName)
        val ivRemove: ImageView = itemView.findViewById(R.id.ivRemove)

        fun bind(person: PersonView, position: Int) {
            etName.setText(person.name)

            etName.addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    people[position].name = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?,
                                           start: Int,
                                           before: Int,
                                           count: Int)
                {}
            }
            )

            ivRemove.setOnClickListener {
                onRemoveClick(position)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PersonViewHolder,
                                  position: Int) {
        holder.bind(people[position], position)
    }

    override fun getItemCount() = people.size

    fun makePerson(person: PersonView) {
        Log.d("makePerson", "makePerson")
        people.add(person)
        notifyItemInserted(people.size - 1 )
    }

    fun removePerson(position: Int) {
        if(people.size>1) {
            people.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getPeople(): List<PersonView> {
        return people.map { PersonView(it.name) }
    }

    data class PersonView (
        var name: String = "",
    )
}