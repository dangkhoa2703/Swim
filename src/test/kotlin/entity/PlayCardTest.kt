package entity

import kotlin.test.*

class PlayCardTest {
    private val aceOfSpades = PlayCard(CardSuit.SPADES, CardValue.ACE)
    private val jackOfClubs = PlayCard(CardSuit.CLUBS, CardValue.JACK)
    private val queenOfHearts = PlayCard(CardSuit.HEARTS, CardValue.QUEEN)
    private val kingOfHearts = PlayCard(CardSuit.HEARTS, CardValue.KING)
    private val jackOfDiamonds = PlayCard(CardSuit.DIAMONDS, CardValue.JACK)

//    @Test
//    fun testCompareSuit() {
//        assertEquals(kingOfHearts, queenOfHearts)
//        assertEquals(jackOfClubs, jackOfDiamonds)
//        assertNotEquals(jackOfClubs, jackOfDiamonds)
//    }
}