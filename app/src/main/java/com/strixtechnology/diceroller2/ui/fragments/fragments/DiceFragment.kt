package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ImageView
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
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setupUi()
        subscribeUi()
        return binding.root
    }

    private fun setupUi() {
        binding.settingsImageView.setOnClickListener {
            lifecycleScope.launch {
                findNavController().navigate(R.id.action_diceFragment_to_settingsFragment)
            }
        }

        binding.rollDiceButton.setOnClickListener {
            viewModel.rollDice()
            animateDice()
        }

        binding.decrementImageView.setOnClickListener {
            viewModel.removeDice()
            animateImageView(binding.decrementImageView)
        }

        binding.incrementImageView.setOnClickListener {
            viewModel.addDice()
            animateImageView(binding.incrementImageView)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeUi() {
        viewModel.diceInformationChanged.observe(viewLifecycleOwner) {
            if (it.selectedAnimationOption) {
                animateDice()
            }
        }

        viewModel.diceTotal.observe(viewLifecycleOwner) { }
        viewModel.dice.observe(viewLifecycleOwner) {
            if (it.size > 0) {
                binding.dice1ImageView.setImageResource(it[0].getDiceImageResourceFor8Sides())
            }
            if (it.size > 1) {
                binding.dice2ImageView.setImageResource(it[1].getDiceImageResourceFor8Sides())
            }
            if (it.size > 2) {
                binding.dice3ImageView.setImageResource(it[2].getDiceImageResourceFor8Sides())
            }
            if (it.size > 3) {
                binding.dice4ImageView.setImageResource(it[3].getDiceImageResourceFor8Sides())
            }
        }
    }

    private fun animateDice() {
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
    }

    private fun animateImageView(imageView: ImageView) {
        //when imageView is clicked, it'll make an alpha effect from 0.1 to 1f(it'll go from grey to it's normal fill color), for the duration of 500 milliseconds
        ObjectAnimator.ofFloat(imageView, View.ALPHA, 0.1f, 1f).apply {
            duration = 500
        }.start()
    }
}








