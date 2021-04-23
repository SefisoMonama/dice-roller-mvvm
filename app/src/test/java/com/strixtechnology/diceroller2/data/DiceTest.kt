package com.strixtechnology.diceroller2.data

import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class DiceTest {
    /*
    * This gets executed before each test is executed
    * */
    @Before
    fun setUp() {
        // There might be something we add here, but for now we can leave it empty
    }

    /*
    * Remember when you had that bug where the sum of dice would always be 0?
    * This as due to the currentDiceValue of a new Dice object defaulting to 0.
    * So let us add a test which always ensures that this never happens again.
    * */
    @Test
    fun new_dice_object_current_value_should_default_to_one(){
        val sixSidedDice = Dice(6)
        val eightSidedDice = Dice(8)
        val expectedValue = 1
        assertEquals(expectedValue, sixSidedDice.currentDiceValue)
        assertEquals(expectedValue, eightSidedDice.currentDiceValue)
    }
}