package com.strixtechnology.diceroller2.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DARK_THEME
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
        private val dataStoreRepository: DataStoreRepository
): ViewModel(){
    //private var diceSides = DEFAULT_DICE_SIDES
    //private var diceNum = DEFAULT_DICE_NUM
    //private var displayTotal = DEFAULT_DISPLAY_DICE_TOTAL


}