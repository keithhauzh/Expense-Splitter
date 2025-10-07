package com.keith.expensesplitter.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.buildIntIntMap
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.keith.expensesplitter.R
import com.keith.expensesplitter.databinding.FragmentEditGroupDialogBinding
import com.keith.expensesplitter.ui.view_models.EditGroupDialogViewModel
import kotlinx.coroutines.launch

class EditGroupDialogFragment : DialogFragment() {
    private val viewModel: EditGroupDialogViewModel by viewModels {
        EditGroupDialogViewModel.Factory
    }
    private lateinit var binding: FragmentEditGroupDialogBinding
    private val args: EditGroupDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditGroupDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener { dismiss() }
        viewModel.getGroup(args.groupId)
        displayGroup()
        onClickDone()


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.finish.collect {
                findNavController().popBackStack(R.id.previousGroupsFragment, false)
            }
        }

        showError()
    }

    fun displayGroup() {
        lifecycleScope.launch {
            viewModel.group.collect {
                binding.run {
                    etEditName.setText(it.name)
                    etEditDetails.setText(it.details)
                }
            }
        }
    }

    fun onClickDone() {
        binding.mbDone.setOnClickListener {
            val editedName = binding.etEditName.text
            val editedDetails = binding.etEditDetails.text
            viewModel.updateGroup(
                name = editedName.toString(),
                details = editedDetails.toString()
            )
        }
    }

    fun showError() = lifecycleScope.launch {
        viewModel.error.collect {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.setBackgroundTint(
                ContextCompat.getColor(requireContext(), R.color.red)
            )
            snackbar.show()
        }
    }
}