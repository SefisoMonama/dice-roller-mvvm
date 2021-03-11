package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding
import com.strixtechnology.diceroller2.viewmodels.DiceViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiceFragment : Fragment() {

    private lateinit var viewModel: DiceViewModel
    private lateinit var binding: FragmentDiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DiceViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiceBinding.inflate(inflater, container, false)
        setupUi()
        subscribeUi()
        return binding.root
    }

    private fun setupUi() {
        binding.settingsImageView.setOnClickListener {
            findNavController().navigate(R.id.action_diceFragment_to_settingsFragment)
        }

        binding.rollDiceButton.setOnClickListener {
            viewModel.rollDice()
        }

        binding.incrementImageView.setOnClickListener {
            viewModel.addDice()
        }

        binding.decrementImageView.setOnClickListener {
            viewModel.removeDice()
        }
    }

    private fun subscribeUi() {
        viewModel.diceInformationChanged.observe(viewLifecycleOwner){
            // This block of code gets called whenever your DataStore values change
            // So it will trigger whenever you load the screen or if you change the dice
            // properties in the  Datastore.

            // Set the appropriate UI values here
        }

        viewModel.dice.observe(viewLifecycleOwner){
            //This block of code gets called whenever your dice model changes

            // Run your app and take a look at logcat - Here I print the value of each dice
            it.forEachIndexed { index, dice ->
                val diceIndex = index + 1
                Log.e("Dice $diceIndex", "current value: " + dice.currentDiceValue.toString())
            }

            // Set the appropriate UI values here
        }
    }
}


