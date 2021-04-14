package com.strixtechnology.diceroller2.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.strixtechnology.diceroller2.data.DataStoreRepository
import com.strixtechnology.diceroller2.data.Dice
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class DiceViewModel @ViewModelInject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {


    private val showWelcomeText = dataStoreRepository.welcomeText.asLiveData()
    private val diceInformation = dataStoreRepository.diceInformation.asLiveData()
    /*
    * LiveData of your Dice model.
    * Read up on what an ArrayList is and how it can be used.
    *
    * Basically, It is like an array containing all of your Dice objects.
    * */
    private val _dice = MutableLiveData<ArrayList<Dice>>(ArrayList())
    val dice: LiveData<ArrayList<Dice>> get() = _dice

    //val showWelcomeText = MutableLiveData<Boolean>(true)
    /*
    * Live data which executes whenever the DataStore values of your dice info changes.
    * In here I update the Dice live data with the new values from the datastore
    * Read up on Transformations.map and Transformations.switchMap - they are important
    * */
    val diceInformationChanged = Transformations.map(diceInformation) {
        val numberOfDice = it.selectedDiceNumbers
        val numberOfSides = it.selectedDiceSides
        val currentDiceModel = _dice.value
        currentDiceModel?.clear()
        for (i in 1..numberOfDice) {
            currentDiceModel?.add(Dice(numberOfSides))
        }
        return@map it
    }

    val diceTotal = Transformations.map(dice) { diceArray ->
        return@map diceArray.sumOf { it.currentDiceValue }
    }

    /*
    * This is called by the fragment when a user taps the roll button.
    * I then go through the list of Dice objects, and roll each one.
    * I then update the Dice liveData, so that the observer in the Fragment gets triggered
    * */
    val welcomeText = Transformations.map(showWelcomeText){
        return@map it.firstTimeUse
    }
    fun rollDice() {
        val currentDice = _dice.value!!
        currentDice.forEach { dice ->
            dice.roll()
        }
        _dice.value = currentDice
    }

    /*
    *this function will remove 1 dice every time it is called
    * we used the help of coroutine to specify the sequential order the function should follow
    * when it is called, it'll remove 1 dice first and roll Dice visible
     */
    fun removeDice() {
        viewModelScope.launch{
            dataStoreRepository.decreaseDiceNumber()
            rollDice()
        }
    }

    /*
    *this function will add 1 dice every time it is called
    * we used the help of coroutine to specify the sequential order the function should follow
    * when it is called, it'll add 1 dice first and roll Dice visible
     */
    fun addDice() {
        viewModelScope.launch{
            dataStoreRepository.increaseDiceNumber()
            rollDice()
        }
    }
}







