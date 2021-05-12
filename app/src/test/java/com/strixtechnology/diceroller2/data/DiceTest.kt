package com.strixtechnology.diceroller2.data

import com.strixtechnology.diceroller2.R
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

    val sixSidedDice = Dice(6)
    val eightSidedDice = Dice(9)

    /*
    * Remember when you had that bug where the sum of dice would always be 0?
    * This as due to the currentDiceValue of a new Dice object defaulting to 0.
    * So let us add a test which always ensures that this never happens again.
    * */
    @Test
    fun new_dice_object_current_value_should_default_to_one() {
        val expectedValue = 1
        assertEquals(expectedValue, sixSidedDice.currentDiceValue)
        assertEquals(expectedValue, eightSidedDice.currentDiceValue)
    }

    @Test
    fun current_dice_value_1_should_display_drawable_dice1() {
        val expectedValue = R.drawable.dice1
        eightSidedDice.currentDiceValue = 1
        val actualValue = eightSidedDice.getDiceImageResource()
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun current_dice_value_2_should_display_drawable_dice2() {
        val expectedValue = R.drawable.dice2
        eightSidedDice.currentDiceValue = 2
        val actualValue = eightSidedDice.getDiceImageResource()
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun current_dice_value_3_should_display_drawable_dice3() {
        val expectedValue = R.drawable.dice3
        eightSidedDice.currentDiceValue = 3
        val actualValue = eightSidedDice.getDiceImageResource()
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun current_dice_value_4_should_display_drawable_dice4() {
        val expectedValue = R.drawable.dice4
        eightSidedDice.currentDiceValue = 4
        val actualValue = eightSidedDice.getDiceImageResource()
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun current_dice_value_5_should_display_drawable_dice5() {
        val expectedValue = R.drawable.dice5
        eightSidedDice.currentDiceValue = 5
        val actualValue = eightSidedDice.getDiceImageResource()
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun current_dice_value_6_should_display_drawable_dice6() {
        val expectedValue = R.drawable.dice6
        eightSidedDice.currentDiceValue = 6
        val actualValue = eightSidedDice.getDiceImageResource()
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun current_dice_value_7_should_display_drawable_dice7() {
        val expectedValue = R.drawable.dice7
        eightSidedDice.currentDiceValue = 7
        val actualValue = eightSidedDice.getDiceImageResource()
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun current_dice_value_8_should_display_drawable_dice8() {
        val expectedValue = R.drawable.dice8
        eightSidedDice.currentDiceValue = 8
        val actualValue = eightSidedDice.getDiceImageResource()
        assertEquals(expectedValue, actualValue)
    }


}