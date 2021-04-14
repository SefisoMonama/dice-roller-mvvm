package com.strixtechnology.diceroller2.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DARK_THEME
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_ANIMATION
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val diceInformation = dataStoreRepository.diceInformation.asLiveData()

    fun saveDiceAnimationOption(diceAnimate: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDiceAnimation(diceAnimate)
        }
    }

    fun saveDiceSides(diceSides: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDiceSides(diceSides)
        }
    }

    fun saveDiceNumbers(diceNumbers: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveDiceNumber(diceNumbers)
        }
    }

    fun saveAppModeSettings(darkTheme: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveAppModeSettings(darkTheme)
        }
    }

    fun saveDisplayDiceTotal(displayDiceTotal: Boolean){
        viewModelScope.launch {
            dataStoreRepository.saveDisplayDiceTotal(displayDiceTotal)
        }
    }
}
