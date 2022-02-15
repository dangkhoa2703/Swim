package entity

import kotlin.test.*

class MiddleTest {
    private val aceOfSpades = PlayCard(CardSuit.SPADES, CardValue.ACE)
    private val jackOfClubs = PlayCard(CardSuit.CLUBS, CardValue.JACK)
    private val jackOfDiamonds = PlayCard(CardSuit.DIAMONDS, CardValue.JACK)
    private val kingOfDiamonds = PlayCard(CardSuit.DIAMONDS, CardValue.KING)
    private val cardList = listOf(aceOfSpades,jackOfClubs,jackOfDiamonds)
    private val middleStack = Middle(cardList)


    @Test
    fun checkMiddleCardsNumber(){
        assertEquals(3,middleStack.middleStack.size)
    }

    @Test
    fun constructorTest(){
        assertTrue(middleStack.middleStack == listOf(aceOfSpades,jackOfClubs,jackOfDiamonds))
        assertFalse(middleStack.middleStack == listOf(aceOfSpades,jackOfClubs,kingOfDiamonds))
    }
}