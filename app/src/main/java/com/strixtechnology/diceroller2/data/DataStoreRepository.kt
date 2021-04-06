package com.strixtechnology.diceroller2.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DARK_THEME
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_ANIMATION
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.DEFAULT_DISPLAY_DICE_TOTAL
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCES_NAME
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DARK_MODE
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DICE_ANIMATION
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DICE_NUM
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DICE_SIDES
import com.strixtechnology.diceroller2.util.Constants.Companion.PREFERENCE_DISPLAY_TOTAL
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedDiceSides = preferencesKey<Int>(PREFERENCE_DICE_SIDES)
        val selectedDiceNumbers = preferencesKey<Int>(PREFERENCE_DICE_NUM)
        val selectedDisplayTotal = preferencesKey<Boolean>(PREFERENCE_DISPLAY_TOTAL)
        val selectedDarkMode = preferencesKey<String>(PREFERENCE_DARK_MODE)
        val selectedDiceAnimation = preferencesKey<Boolean>(PREFERENCE_DICE_ANIMATION)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    /*
    *this suspend function saves numbers of sides a dice should have to dataStore using preferences
     */
    suspend fun saveDiceSides(diceSides: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceSides] = diceSides
        }
    }

    /*
    *this suspend function saves dice Animation Option selected to dataStore using preferences
     */
    suspend fun saveDiceAnimation(diceAnimate: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceAnimation] = diceAnimate
        }
    }

    /*
    *this suspend function saves dice Numbers to be rolled to dataStore using preferences
     */
    suspend fun saveDiceNumber(diceNumbers: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceNumbers] = diceNumbers
        }
    }

    /*
    *this suspend function saves option selected (whether to display or !display dice Total), as boolean value to dataStore using preferences
     */
    suspend fun saveDisplayDiceTotal(displayTotal: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDisplayTotal] = displayTotal
        }
    }

    /*
    * This function save App mode settings as selected, to dataStore using preferences
     */
    suspend fun saveAppModeSettings(darkMode: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDarkMode] = darkMode
        }
    }

    /*
    * I added a model which contains the 2 values required for the dice.
    * It just made things easier since I didn't want to subscribe to each
    * variable individually in the viewModel.
    *
    * This entire class can be simplified - but I will leave it to you so that I don't
    * confuse you any further
    * */
    var diceInformation: Flow<DiceInformation> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedDiceSides =
                preferences[PreferenceKeys.selectedDiceSides] ?: DEFAULT_DICE_SIDES
            val selectedDiceNumbers =
                preferences[PreferenceKeys.selectedDiceNumbers] ?: DEFAULT_DICE_NUM
            return@map DiceInformation(selectedDiceNumbers, selectedDiceSides)
        }

    /*
    *This function will decrease diceNumber by 1, every time it is called
     */
    suspend fun decreaseDiceNumber() {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceNumbers] =
                preferences[PreferenceKeys.selectedDiceNumbers]!! - 1
        }
    }

    /*
    *This function will increase diceNumber by 1, every time it is called
     */
    suspend fun increaseDiceNumber() {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedDiceNumbers] =
                preferences[PreferenceKeys.selectedDiceNumbers]!! + 1
        }
    }

    /*
    *flow of DiceSides, will return selected dice sides or default Dice sides
     */
    var readDiceSides: Flow<DiceSides> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedDiceSides =
                preferences[PreferenceKeys.selectedDiceSides] ?: DEFAULT_DICE_SIDES
            DiceSides(selectedDiceSides)
        }


    /*
    *flow of DiceNumbers, will return selected dice numbers or default Dice number of 1
     */
    val readDiceNumbers: Flow<DiceNumbers> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedDiceNumbers =
                preferences[PreferenceKeys.selectedDiceNumbers] ?: DEFAULT_DICE_NUM
            DiceNumbers(selectedDiceNumbers)
        }


    val readDisplayDiceTotal: Flow<DisplayDiceTotal> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedDisplayTotal =
                preferences[PreferenceKeys.selectedDisplayTotal] ?: DEFAULT_DISPLAY_DICE_TOTAL
            return@map DisplayDiceTotal(selectedDisplayTotal)
        }

        val readAppModeSettings: Flow<AppModeSettings> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val selectedDarkMode =
                    preferences[PreferenceKeys.selectedDarkMode] ?: DEFAULT_DARK_THEME
                AppModeSettings(selectedDarkMode)
            }


        val readDiceAnimationOption: Flow<DiceAnimation> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val selectedAnimation =
                    preferences[PreferenceKeys.selectedDiceAnimation] ?: DEFAULT_DICE_ANIMATION
                return@map DiceAnimation(selectedAnimation)
            }


    }

    data class DiceInformation(
        var numberOfDice: Int,
        val numberOfSidesPerDice: Int
    )

    data class DiceAnimation(
        var selectedAnimationOption: Boolean
    )

    data class DiceSides(
        val selectedDiceSides: Int
    )

    data class DiceNumbers(
        val selectedDiceNumbers: Int
    )

    data class DisplayDiceTotal(
        val selectedDisplayTotal: Boolean
    )

    data class AppModeSettings(
        val selectedDarkMode: String
    )