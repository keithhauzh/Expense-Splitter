package com.keith.expensesplitter.ui.adapters

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.keith.expensesplitter.data.model.Group
import com.keith.expensesplitter.databinding.ItemLayoutGroupBinding
import com.keith.expensesplitter.ui.fragments.PreviousGroupsFragmentDirections
import com.keith.expensesplitter.ui.fragments.PreviousGroupsViewModel

class GroupsAdapter (
    private var groups: List<Group>,
    private var onClick: (Group) -> Unit,
    private var viewModel: PreviousGroupsViewModel
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

    fun showDeleteConfirmationDialog(id: Long?, context: Context) {
        if (id!=null){
            AlertDialog.Builder(context)
                .setTitle("Delete confirmation")
                .setNegativeButton("Delete") {dialog, _ ->
                    viewModel.deleteGroup(id)
                    dialog.dismiss()
                    viewModel.getGroups()
                }
                .setPositiveButton("Cancel") {dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }else{
            Log.d("GroupAdapter", "Could not delete group with null id: $id")
        }
    }

    fun showEditDialog(id: Long?, root: CardView) {
        if(id!=null) {
            val action = PreviousGroupsFragmentDirections
                .actionPreviousGroupsFragmentToEditGroupDialogFragment(id)
            root.findNavController().navigate(action)
        }else{
            Log.d("GroupsAdapter", "Could not find group with null id: $id")
        }
    }


    inner class GroupViewHolder(
        private val binding: ItemLayoutGroupBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(group: Group){
            binding.run {
                tvName.text = group.name
                tvDetails.text = group.details
                ivEdit.setOnClickListener {
                    showEditDialog(group.id, binding.root)
                }
                ivDelete.setOnClickListener {
                    showDeleteConfirmationDialog(group.id, binding.root.context)
                }
                llGroup.setOnClickListener {
                    onClick(group)
                }
            }
        }
    }
}