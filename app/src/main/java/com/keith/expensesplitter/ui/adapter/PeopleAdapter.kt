package com.keith.expensesplitter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keith.expensesplitter.data.model.Person
import com.keith.expensesplitter.databinding.ItemLayoutPersonBinding

class PeopleAdapter(
    private var people: List<Person>
): RecyclerView.Adapter<PeopleAdapter.PersonViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PeopleAdapter.PersonViewHolder {
        val binding = ItemLayoutPersonBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = people[position]
        holder.bind(person)
    }

    override fun getItemCount() = people.size

    inner class PersonViewHolder(
        private val binding: ItemLayoutPersonBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind (person: Person) {
            binding.run {
            }
        }
    }
}