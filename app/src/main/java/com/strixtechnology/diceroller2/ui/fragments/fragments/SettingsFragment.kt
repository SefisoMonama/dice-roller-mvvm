package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.FragmentSettingsBinding
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DARK_THEME
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import com.strixtechnology.diceroller2.viewmodels.MainViewModel
import com.strixtechnology.diceroller2.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    //private lateinit var mainViewModel: MainViewModel
    private lateinit var settingsViewModel: SettingsViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {

        //}
        settingsViewModel = ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentSettingsBinding.inflate(layoutInflater)

        val toolbar = binding.settingsToolBar
        toolbar.setNavigationIcon(R.drawable.ic_back_home_arrow)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        settingsViewModel.readDiceSides.asLiveData().observe(viewLifecycleOwner, { value ->
            settingsViewModel.diceSidesChip = value.selectedDiceSides
            updateChip(value.selectedDiceSidesId, binding.diceSidesChipGroup)
        })

        settingsViewModel.readDiceNumbers.asLiveData().observe(viewLifecycleOwner, { value ->
            settingsViewModel.diceNumberChip = value.selectedDiceNumbers
            updateChip(value.selectedDiceNumbersId, binding.diceNumberChipGroup)
        })

        settingsViewModel.readDisplayDiceTotal.asLiveData().observe(viewLifecycleOwner, { value ->
            settingsViewModel.displayTotalChip = value.selectedDisplayTotal
            updateChip(value.selectedDisplayTotalId, binding.displayTotalChipGroup)
        })


        settingsViewModel.readAppModeSettings.asLiveData().observe(viewLifecycleOwner, { value ->
            settingsViewModel.darkThemeChip = value.selectedDarkMode
           updateChip(value.selectedDarkModeId, binding.darkModeChipGroup)
        })


        binding.diceSidesChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDiceSides = chip.text.toString().toLowerCase(Locale.ROOT)
            settingsViewModel.diceSidesChip = selectedDiceSides.toInt()
            settingsViewModel.diceSidesChipId = selectedChipId
        }

        binding.diceNumberChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDiceNumbers = chip.text.toString().toLowerCase(Locale.ROOT)
            settingsViewModel.diceNumberChip = selectedDiceNumbers.toInt()
            settingsViewModel.diceNumberChipId = selectedChipId
        }

        binding.displayTotalChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDisplayTotalOption = chip.text.toString().toLowerCase(Locale.ROOT)
            settingsViewModel.displayTotalChip = selectedDisplayTotalOption
            settingsViewModel.displayTotalChipId = selectedChipId
        }

        binding.darkModeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDarkThemeOption = chip.text.toString().toLowerCase(Locale.ROOT)
            settingsViewModel.darkThemeChip = selectedDarkThemeOption
            settingsViewModel.darkThemeId = selectedChipId

            settingsViewModel.saveAppModeSettings(settingsViewModel.darkThemeChip, settingsViewModel.darkThemeId)

            if(chip == binding.disableDarkModeChip){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        binding.saveSettingsButton.setOnClickListener{
            settingsViewModel.saveDiceSides(settingsViewModel.diceSidesChip,settingsViewModel.diceSidesChipId)
            settingsViewModel.saveDiceNumbers(settingsViewModel.diceNumberChip, settingsViewModel.diceNumberChipId)
            settingsViewModel.saveDisplayTotalSettings(settingsViewModel.displayTotalChip, settingsViewModel.displayTotalChipId)
            Toast.makeText(context, "Settings saved!", Toast.LENGTH_SHORT).show()
        }


        binding.contactSupportButton.setOnClickListener {
            sendMail()
        }

        return binding.root
    }

    fun sendMail(){
        val intent =  Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "sefiso@strixtechnology.com", null))
        startActivity(Intent.createChooser(intent, "Send mail to DiceRoller.co support"))
    }

   private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("DiceSettings", e.message.toString())
            }
        }
    }
}

