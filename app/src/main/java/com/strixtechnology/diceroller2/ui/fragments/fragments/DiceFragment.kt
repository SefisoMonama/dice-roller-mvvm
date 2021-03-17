package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding
import com.strixtechnology.diceroller2.viewmodels.DiceViewModel
import com.strixtechnology.diceroller2.viewmodels.MainViewModel
import com.strixtechnology.diceroller2.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.max


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
        savedInstanceState: Bundle?,
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
            binding.welcomeTextView.visibility = View.GONE
            binding.welcomeInstructionsTextView.visibility = View.GONE

            viewModel.displayDiceTotal.observe(viewLifecycleOwner) {
                if (it.selectedDisplayTotalId == R.id.yes_chip) {
                    binding.totalTextView.visibility = View.VISIBLE
                } else {
                    binding.totalTextView.visibility = View.GONE
                }
            }

            viewModel.rollDice()
        }

        binding.incrementImageView.setOnClickListener {
            viewModel.addDice()
        }

        binding.decrementImageView.setOnClickListener {
            viewModel.removeDice()
            Log.e("Dice", "Dice Removed")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeUi() {
        viewModel.diceInformationChanged.observe(viewLifecycleOwner) {
            // This block of code gets called whenever your DataStore values change
            // So it will trigger whenever you load the screen or if you change the dice
            // properties in the  Datastore.

            // Set the appropriate UI values here
        }



        viewModel.dice.observe(viewLifecycleOwner) {
            //This block of code gets called whenever your dice model changes

            // Run your app and take a look at logcat - Here I print the value of each dice
            it.forEachIndexed { index, dice ->
                val diceIndex = index + 1
                Log.e("Dice $diceIndex", "current value: " + dice.currentDiceValue.toString())

            }

            // Set the appropriate UI values here
            it.forEachIndexed { index, dice ->
                val diceIndex = index + 1
                var sum = 0
                for(i in 1..diceIndex){
                    sum += dice.currentDiceValue
                    binding.totalTextView.text = "You Rolled: " + sum.toString()
                }


                when (diceIndex) {
                    1 -> {
                        binding.dice1ImageView.visibility = View.VISIBLE
                        binding.dice1ImageView.setImageResource(dice.getDiceImageResourceFor8Sides())
                        binding.dice2ImageView.visibility = View.GONE
                        binding.dice3ImageView.visibility = View.GONE
                        binding.dice4ImageView.visibility = View.GONE
                    }
                    2 -> {
                        binding.dice2ImageView.visibility = View.VISIBLE
                        binding.dice2ImageView.setImageResource(dice.getDiceImageResourceFor8Sides())
                        binding.dice3ImageView.visibility = View.GONE
                        binding.dice4ImageView.visibility = View.GONE
                    }
                    3 -> {
                        binding.dice3ImageView.visibility = View.VISIBLE
                        binding.dice3ImageView.setImageResource(dice.getDiceImageResourceFor8Sides())
                        binding.dice4ImageView.visibility = View.GONE
                    }
                    4 -> {
                        binding.dice4ImageView.visibility = View.VISIBLE
                        binding.dice4ImageView.setImageResource(dice.getDiceImageResourceFor8Sides())
                    }
                }
            }
        }
    }

        override fun onResume() {
            binding.dice1ImageView.visibility = View.GONE
            binding.dice2ImageView.visibility = View.GONE
            binding.dice3ImageView.visibility = View.GONE
            binding.dice4ImageView.visibility = View.GONE
            binding.totalTextView.visibility = View.GONE
            super.onResume()
        }
    }


