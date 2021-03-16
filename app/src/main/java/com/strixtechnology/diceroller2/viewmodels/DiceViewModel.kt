package com.strixtechnology.diceroller2.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.data.Dice
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DiceViewModel @ViewModelInject constructor(
    val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    /*
    * LiveData of the Dice properties (number of dice and number of sides)
    * Whenever the values in the DataStore change, this variable will trigger a change
    * to all its observers
    * */
    private val diceInformation = dataStoreRepository.diceInformation.asLiveData()
    val displayDiceTotal = dataStoreRepository.readDisplayDiceTotal.asLiveData()

    /*
    * LiveData of your Dice model.
    * Read up on what an ArrayList is and how it can be used.
    *
    * Basically, It is like an array containing all of your Dice objects.
    * */
    private val _dice = MutableLiveData<ArrayList<Dice>>(ArrayList())
    val dice: LiveData<ArrayList<Dice>> get() = _dice

    /*
    * Live data which executes whenever the DataStore values of your dice info changes.
    *
    * In here I update the Dice live data with the new values from the datastore
    *
    * Read up on Transformations.map and Transformations.switchMap - they are important
    * */
    val diceInformationChanged = Transformations.map(diceInformation) {
        val numberOfDice = it.numberOfDice
        val numberOfSides = it.numberOfSidesPerDice
        val currentDiceModel = _dice.value
        currentDiceModel?.clear()
        for (i in 1..numberOfDice) {
            currentDiceModel?.add(Dice(numberOfSides))
        }
    }
    /*
    * This is called by the fragment when a user taps the roll button.
    * I then go through the list of Dice objects, and roll each one.
    *
    * I then update the Dice liveData, so that the observer in the Fragment gets triggered
    * */
    fun rollDice() {
        val currentDice = _dice.value!!
        currentDice.forEach { dice ->
            dice.roll()
        }
        _dice.value = currentDice
    }

    fun removeDice() {
        var currentDice = _dice.value
        currentDice!!.remove(currentDice)
        _dice.value = currentDice
        // Here you need to decrease the appropriate value in the dataStore
    }

    fun addDice() {

            /**var currentDice = _dice.value
            currentDice = dataStoreRepository.diceInformation()
            _dice.value = currentDice*/
            // Here you need to increase the appropriate value in the dataStore
        }

    }




