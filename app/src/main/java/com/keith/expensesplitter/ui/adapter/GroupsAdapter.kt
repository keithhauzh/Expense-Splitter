package com.keith.expensesplitter.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.keith.expensesplitter.data.model.Group
import com.keith.expensesplitter.databinding.ItemLayoutGroupBinding

class GroupsAdapter (
    private var groups: List<Group>
): RecyclerView.Adapter<GroupsAdapter.GroupViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):  {

    }


    inner class GroupViewHolder(
        private val binding: ItemLayoutGroupBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(group: Group){
            binding.run {}
        }
    }
}