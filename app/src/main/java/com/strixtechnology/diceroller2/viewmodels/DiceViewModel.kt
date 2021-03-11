package com.strixtechnology.diceroller2.viewmodels

import android.content.Context
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.data.Dice
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import kotlinx.coroutines.launch

class DiceViewModel @ViewModelInject constructor(
        val dataStoreRepository: DataStoreRepository
) : ViewModel() {


    private val getSidesCount = dataStoreRepository.readDiceSides
    private val getDiceCount = dataStoreRepository.readDiceNumbers
    //val _dice: MutableLiveData<ArrayList<Dice>>? = null
    //val dice: LiveData<ArrayList<Dice>>? = null

    /**val diceObj = Dice(getSidesCount)

    val currentDice = _dice!!.value

    fun rollDice() {
        for (dice in getSidesCount){
            diceObj.roll()
        }
    }
    fun removeDice(){
        currentDice!!.removeLast()
        _dice!!.value = currentDice
    }

    fun addDice() {
        val newDice = diceObj
        currentDice!!.add(newDice)
        _dice!!.value = currentDice
    }*/
}




