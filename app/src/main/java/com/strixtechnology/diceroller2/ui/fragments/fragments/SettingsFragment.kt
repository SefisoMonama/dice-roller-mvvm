package com.strixtechnology.diceroller2.ui.fragments.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.databinding.FragmentSettingsBinding
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DARK_THEME
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import com.strixtechnology.diceroller2.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val args by navArgs<SettingsFragmentArgs>()
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var mainViewModel: MainViewModel
    //private lateinit var mToolbar: Toolbar

    private var diceSidesChip = DEFAULT_DICE_SIDES
    private var diceSidesChipId = 0
    private var diceNumberChip = DEFAULT_DICE_NUM
    private var diceNumberChipId = 0
    private var displayTotalChip = DEFAULT_DISPLAY_DICE_TOTAL
    private var displayTotalChipId = 0

    private var darkTheme = DEFAULT_DARK_THEME
    private var darkThemeId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_settings, container, false)

        binding = FragmentSettingsBinding.inflate(layoutInflater)


        //val toolbar = binding.settingsToolBar


        mainViewModel.readDiceSettings.asLiveData().observe(viewLifecycleOwner, { value ->
            diceSidesChip = value.selectedDiceSides
            diceNumberChip = value.selectedDiceNumbers
            displayTotalChip = value.selectedDisplayTotal
            updateChip(value.selectedDiceSidesId, binding.diceSidesChipGroup)
            updateChip(value.selectedDiceNumbersId, binding.diceNumberChipGroup)
            updateChip(value.selectedDisplayTotalId, binding.displayTotalChipGroup)
        })

        mainViewModel.readAppSettings.asLiveData().observe(viewLifecycleOwner, { value ->
            darkTheme = value.selectedDarkMode
            updateChip(value.selectedDarkModeId, binding.darkModeChipGroup)
        })

        binding.diceSidesChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDiceSides = chip.text.toString().toLowerCase(Locale.ROOT)
            diceSidesChip = selectedDiceSides
            diceSidesChipId = selectedChipId
        }

        binding.diceNumberChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDiceNumbers = chip.text.toString().toLowerCase(Locale.ROOT)
            diceNumberChip = selectedDiceNumbers
            diceNumberChipId = selectedChipId
        }

        binding.displayTotalChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDisplayTotalOption = chip.text.toString().toLowerCase(Locale.ROOT)
            displayTotalChip = selectedDisplayTotalOption
            displayTotalChipId = selectedChipId
        }

        binding.darkModeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDarkThemeOption = chip.text.toString().toLowerCase(Locale.ROOT)
            darkTheme = selectedDarkThemeOption
            darkThemeId = selectedChipId
        }

        binding.saveSettingsButton.setOnClickListener{
            mainViewModel.saveDiceSettings(
                    diceSidesChipId,
                    diceSidesChip,
                    diceNumberChipId,
                    diceNumberChip,
                    displayTotalChipId,
                    displayTotalChip,
            )
            Toast.makeText(context, "Settings saved!", Toast.LENGTH_SHORT).show()
        }


        binding.darkModeChipGroup.setOnCheckedChangeListener { group, checkedId ->
            mainViewModel.saveAppSettings(
                    darkThemeId,
                    darkTheme
            )
        }

        binding.contactSupportButton.setOnClickListener {
            sendMail()
          }

        setHasOptionsMenu(true)
        return binding.root
    }

    /**override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settings_menu){
            findNavController().navigate(R.id.action_settingsFragment_to_diceFragment)
            args.fromSettingsFragment
        }
        return super.onOptionsItemSelected(item)
    }*/

    fun sendMail(){
        val intent =  Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "sefiso@strixtechnology.com", null))
        startActivity(Intent.createChooser(intent, "Sefiso"))
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