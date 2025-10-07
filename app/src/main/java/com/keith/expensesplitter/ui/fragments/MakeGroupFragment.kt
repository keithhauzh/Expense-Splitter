package com.keith.expensesplitter.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.keith.expensesplitter.R
import com.keith.expensesplitter.databinding.FragmentMakeGroupBinding
import com.keith.expensesplitter.ui.view_models.ActivityViewModel
import com.keith.expensesplitter.ui.view_models.MakeGroupViewModel
import kotlinx.coroutines.launch

class MakeGroupFragment : Fragment() {
    private lateinit var binding: FragmentMakeGroupBinding

    private val viewModel: MakeGroupViewModel by viewModels()

    private val activityViewModel: ActivityViewModel by activityViewModels{
        ActivityViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentMakeGroupBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.resetGroup()
        next()
        observeGroupCreation()
    }

    private fun next(){
        binding.mbNext.setOnClickListener {
            observeErrors()
            viewModel.makeGroup(
                name = binding.etName.text.toString(),
                details = binding.etDetails.text.toString()
            )
        }
    }

    private fun observeGroupCreation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.group.collect{ group ->
                Log.d("group-created", group.toString())
                group?.let {
                    activityViewModel.holdGroup(it)
                    navigateToMakeExpenses()
                }
            }
        }
    }

    private fun observeErrors() {
        lifecycleScope.launch {
            viewModel.error.collect { error  ->
                if(error.isNotBlank()){
                    showErrors(error)
                }
            }
        }
    }

    private fun navigateToMakeExpenses(){
        val action = MakeGroupFragmentDirections
            .actionMakeGroupFragmentToMakeExpenseFragment()
        findNavController().navigate(action)
        viewModel.resetGroup()
    }

    private fun showErrors(error: String){
        val snackbar = Snackbar.make(
            binding.root,
            error,
            Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(
            ContextCompat.getColor(requireContext(), R.color.red)
        )
        snackbar.show()
    }
}
