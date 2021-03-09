package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding
import com.strixtechnology.diceroller2.viewmodels.DiceViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiceFragment : Fragment() {


    private lateinit var diceViewModel: DiceViewModel
    private lateinit var binding: FragmentDiceBinding
    private lateinit var rotateAnimation: RotateAnimation
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiceBinding.inflate(inflater, container, false)


        binding.settingsImageView.setOnClickListener {
            val rotateAnimation = RotateAnimation(0.0F, 90F, RotateAnimation.RELATIVE_TO_SELF, .5f, RotateAnimation.RELATIVE_TO_SELF, .5f)
            rotateAnimation.duration = 200
            binding.settingsImageView.startAnimation(rotateAnimation)
            findNavController().navigate(R.id.action_diceFragment_to_settingsFragment)
        }

        binding.rollDiceButton.setOnClickListener {
                val toast = Toast.makeText(context, "Please add dice, using '+' button below", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                val rotateAnimation = RotateAnimation(270F, 0F, RotateAnimation.RELATIVE_TO_SELF, .3f, RotateAnimation.RELATIVE_TO_SELF, .3f)
                rotateAnimation.duration = 20
                binding.incrementImageView.startAnimation(rotateAnimation)
        }


        binding.incrementImageView.setOnClickListener {

        }
        setHasOptionsMenu(true)

        return binding.root
    }


}