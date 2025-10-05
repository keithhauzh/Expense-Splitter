package com.keith.expensesplitter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keith.expensesplitter.data.model.Person
import com.keith.expensesplitter.databinding.ItemLayoutListPersonBinding

class PeopleListAdapter(
    private var people: List<Person>
): RecyclerView.Adapter<PeopleListAdapter.PeopleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PeopleViewHolder {
        val binding = ItemLayoutListPersonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder,
                                  position: Int) {
        val person = people[position]
        holder.bind(person)
    }

    fun setPeople(people: List<Person>) {
        this.people = people
        notifyDataSetChanged()
    }

    override fun getItemCount() = people.size

    inner class PeopleViewHolder(
        private val binding: ItemLayoutListPersonBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(person: Person){
            binding.run {
                tvPersonItemName.text = person.name
            }
        }
    }
}
