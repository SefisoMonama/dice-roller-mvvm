package com.strixtechnology.diceroller2.viewmodels

import android.content.Context
import android.location.GnssNavigationMessage
import android.view.View
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.strixtechnology.diceroller2.R
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.data.DiceSides
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import kotlinx.coroutines.launch

class DiceViewModel @ViewModelInject constructor(
        val dataStoreRepository: DataStoreRepository
) : ViewModel() {


    var diceNumber = dataStoreRepository.readDiceNumbers
    private val _dice: MutableLiveData<ArrayList<DataStoreRepository>> = MutableLiveData()
    //val _dice: MutableLiveData<ArrayList<Dice>>()
   // val dice: LiveData<ArrayList<DataStoreRepository>>


    /**fun rollDice() {
        val currentDice = _dice.value
        for (dice in currentDice){
            dice.roll()
        }
    }
    fun removeDice(){
        var currentDice = _dice.value
        currentDice.removeLast()
        _dice.value = currentDice
    }
    fun addDice() {
        var currentDice = _dice.value
        val newDice = Dice(numberSides)
        currentDice.add(newDice)
        _dice.value = currentDice
    }*/

    fun readDiceSides() {
        viewModelScope.launch {
            dataStoreRepository.readDiceSides
        }
    }
}




