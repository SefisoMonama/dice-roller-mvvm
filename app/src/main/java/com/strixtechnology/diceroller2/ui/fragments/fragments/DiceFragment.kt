package com.strixtechnology.diceroller2.ui.fragments.fragments


import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.FragmentDiceBinding
import com.strixtechnology.diceroller2.viewmodels.DiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
                animateImageView(binding.settingsImageView)
                delay(150)
                findNavController().navigate(R.id.action_diceFragment_to_settingsFragment)
            }
        }

        //open dialog onClick
        binding.infoImageView.setOnClickListener {
            dialogue()
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

            if (it.selectedDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }
        /*
        *will observe dice model for changes - and assign a view with a drawable, every time the dice model value changes
         */
        viewModel.dice.observe(viewLifecycleOwner) {
            if (it.size > 0) {
                binding.dice1ImageView.setImageResource(it[0].getDiceImageResource())
            }
            if (it.size > 1) {
                binding.dice2ImageView.setImageResource(it[1].getDiceImageResource())
            }
            if (it.size > 2) {
                binding.dice3ImageView.setImageResource(it[2].getDiceImageResource())
            }
            if (it.size > 3) {
                binding.dice4ImageView.setImageResource(it[3].getDiceImageResource())
            }
        }
    }

    /*
    *will rotate specified view from 0 - 360 degrees, for 500 milliseconds
    */
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
        //when imageView is clicked - it'll zoom in & out - in a space of 100 milliseconds
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5F, 1F)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5F, 1F)

        ObjectAnimator.ofPropertyValuesHolder(imageView, scaleY, scaleX)
            .apply {
                duration = 100
            }.start()
    }

    //when this function is called it open information dialog - alertDialog
    private fun dialogue(){
        val dialog = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.information_dialog, null)
        dialog.setView(dialogView)
        dialog.show()
    }

}






