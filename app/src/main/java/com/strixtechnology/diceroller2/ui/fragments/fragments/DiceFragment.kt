package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
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
               if (it.selectedDisplayTotal) {
                   binding.totalTextView.visibility = View.VISIBLE
               } else {
                   binding.totalTextView.visibility = View.GONE
               }
            }

            /*
            *This line of code will wait for selected dice animation to be true, and it'll roll visible dice from 0 - 360, for 500 milliseconds
            */
            viewModel.addDiceAnimation.observe(viewLifecycleOwner){
                if (it.selectedAnimationOption){
                    ObjectAnimator.ofFloat(binding.dice1ImageView, View.ROTATION, 0f, 360f).apply {
                        duration = 500
                    }.start()

                    ObjectAnimator.ofFloat(binding.dice2ImageView, View.ROTATION, 0f, 360f).apply {
                        duration = 500
                    }.start()

                    ObjectAnimator.ofFloat(binding.dice3ImageView, View.ROTATION, 0f, 360f).apply {
                        duration = 500
                    }.start()

                    ObjectAnimator.ofFloat(binding.dice4ImageView, View.ROTATION, 0f, 360f).apply {
                        duration = 500
                    }.start()
                    viewModel.rollDice()
                }else{
                    viewModel.rollDice()
                }
            }

            //PropertyValueHolder to specify Scale and Alpha values
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5F, 1F)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5F, 1F)
            val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)

            ObjectAnimator.ofPropertyValuesHolder(binding.rollDiceButton, scaleX, scaleY, alpha)
                .apply {
                    interpolator = OvershootInterpolator()
                }.start()


        }

        binding.decrementImageView.setOnClickListener {
            binding.welcomeTextView.visibility = View.GONE
            binding.welcomeInstructionsTextView.visibility = View.GONE
            viewModel.removeDice()

            //when imageView is clicked, it'll make an alpha effect from 0.1 to 1f(it'll go from grey to it's normal fill color)
            ObjectAnimator.ofFloat(binding.decrementImageView, View.ALPHA, 0.1f, 1f).apply {
                duration = 500
            }.start()
        }

        binding.incrementImageView.setOnClickListener {
            binding.welcomeTextView.visibility = View.GONE
            binding.welcomeInstructionsTextView.visibility = View.GONE
            viewModel.addDice()

            //when imageView is clicked, it'll make an alpha effect from 0.1 to 1f(it'll go from grey to it's normal fill color)
            ObjectAnimator.ofFloat(binding.incrementImageView, View.ALPHA, 0.1f, 1f).apply {
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
                            //when dice index is 1, decrement image view will be disabled (avoid the user having negative dice number
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
                            //when dice index is 4, increment image view will be disabled (avoid the user having more than 4 dice numbers)
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





