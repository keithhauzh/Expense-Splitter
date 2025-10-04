package com.keith.expensesplitter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keith.expensesplitter.data.model.Group
import com.keith.expensesplitter.databinding.ItemLayoutGroupBinding

class GroupsAdapter (
    private var groups: List<Group>,
    private var onClick: (Group) -> Unit
): RecyclerView.Adapter<GroupsAdapter.GroupViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupViewHolder  {
        val binding = ItemLayoutGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
            )
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groups[position]
        holder.bind(group)
    }

    override fun getItemCount() = groups.size

    fun setGroups(groups: List<Group>) {
        this.groups = groups
        notifyDataSetChanged()
    }

    inner class GroupViewHolder(
        private val binding: ItemLayoutGroupBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(group: Group){
            binding.run {
                tvName.text = group.name
                tvDetails.text = group.details
                llGroup.setOnClickListener {
                    onClick(group)
                }
            }
        }
    }
}