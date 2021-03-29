package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding
import com.strixtechnology.diceroller2.viewmodels.DiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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

        binding.decrementImageView.setOnClickListener {
            binding.welcomeTextView.visibility = View.GONE
            binding.welcomeInstructionsTextView.visibility = View.GONE
            viewModel.removeDice()

            ObjectAnimator.ofFloat(binding.decrementImageView, View.ALPHA, 0.3f,1f).apply {
                duration = 500
            }.start()

            Log.e("Dice", "Dice Removed")
        }


        binding.incrementImageView.setOnClickListener {
            binding.welcomeTextView.visibility = View.GONE
            binding.welcomeInstructionsTextView.visibility = View.GONE
            viewModel.addDice()

            ObjectAnimator.ofFloat(binding.incrementImageView, View.ALPHA, 0.3f,1f).apply {
                duration = 500
            }.start()

            Log.e("Dice", "Dice Added")
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

            // Set the appropriate UI values here
            it.forEachIndexed { index, _ ->
                when (index) {
                    0 -> {
                        val firstDiceValue = it[0].currentDiceValue
                        binding.totalTextView.text = "Dice total : $firstDiceValue"
                    }
                    1 -> {
                        val secondDiceValue = it[1].currentDiceValue
                        val firstDiceValue = it[0].currentDiceValue
                        val diceTotal = firstDiceValue + secondDiceValue
                        binding.totalTextView.text = "Dice total : $diceTotal"
                    }
                    2 -> {
                        val thirdDiceValue = it[2].currentDiceValue
                        val secondDiceValue = it[1].currentDiceValue
                        val firstDiceValue = it[0].currentDiceValue
                        val diceTotal = firstDiceValue + secondDiceValue + thirdDiceValue
                        binding.totalTextView.text = "Dice total : $diceTotal"
                    }
                    3 -> {
                        val fourthDiceValue = it[3].currentDiceValue
                        val thirdDiceValue = it[2].currentDiceValue
                        val secondDiceValue = it[1].currentDiceValue
                        val firstDiceValue = it[0].currentDiceValue
                        val diceTotal =
                            firstDiceValue + secondDiceValue + thirdDiceValue + fourthDiceValue
                        binding.totalTextView.text = "Dice total : $diceTotal"
                    }
                }


                it.forEachIndexed { index, dice ->
                    var diceIndex = index + 1
                    when (diceIndex) {
                        1 -> {
                            binding.incrementImageView.isEnabled = true
                            binding.decrementImageView.isEnabled = false
                            binding.dice1ImageView.visibility = View.VISIBLE
                            binding.dice1ImageView.setImageResource(dice.getDiceImageResourceFor8Sides())
                            binding.dice2ImageView.visibility = View.GONE
                            binding.dice3ImageView.visibility = View.GONE
                            binding.dice4ImageView.visibility = View.GONE
                        }
                        2 -> {
                            binding.incrementImageView.isEnabled = true
                            binding.decrementImageView.isEnabled = true
                            binding.dice2ImageView.visibility = View.VISIBLE
                            binding.dice2ImageView.setImageResource(dice.getDiceImageResourceFor8Sides())
                            binding.dice3ImageView.visibility = View.GONE
                            binding.dice4ImageView.visibility = View.GONE
                        }
                        3 -> {
                            binding.incrementImageView.isEnabled = true
                            binding.decrementImageView.isEnabled = true
                            binding.dice3ImageView.visibility = View.VISIBLE
                            binding.dice3ImageView.setImageResource(dice.getDiceImageResourceFor8Sides())
                            binding.dice4ImageView.visibility = View.GONE
                        }
                        4 -> {
                            binding.incrementImageView.isEnabled = false
                            binding.decrementImageView.isEnabled = true
                            binding.dice4ImageView.visibility = View.VISIBLE
                            binding.dice4ImageView.setImageResource(dice.getDiceImageResourceFor8Sides())
                        }
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
        binding.welcomeInstructionsTextView.visibility = View.VISIBLE
        binding.welcomeTextView.visibility = View.VISIBLE
        super.onResume()
    }
}





