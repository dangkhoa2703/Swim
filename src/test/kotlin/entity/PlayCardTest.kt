package entity

import kotlin.test.*

/**
 * TODO Test the installation of the play cards
 *
 */
class PlayCardTest {
    private val aceOfSpades = PlayCard(CardSuit.SPADES, CardValue.ACE)
    private val jackOfClubs = PlayCard(CardSuit.CLUBS, CardValue.JACK)
    private val queenOfHearts = PlayCard(CardSuit.HEARTS, CardValue.QUEEN)
    private val kingOfHearts = PlayCard(CardSuit.HEARTS, CardValue.KING)
    private val jackOfDiamonds = PlayCard(CardSuit.DIAMONDS, CardValue.JACK)
    private val sevenOfDiamonds = PlayCard(CardSuit.DIAMONDS, CardValue.SEVEN)

    /**
     * TODO compare suit of two cards
     *
     */
    @Test
    fun testSuitInit() {
        assertEquals(kingOfHearts.suit, queenOfHearts.suit)
        assertEquals(sevenOfDiamonds.suit, jackOfDiamonds.suit)
        assertNotEquals(jackOfClubs.suit, jackOfDiamonds.suit)
    }

    /**
     * TODO compare value of two cards
     *
     */
    @Test
    fun testValueInit(){
        assertEquals(queenOfHearts.value,kingOfHearts.value)
        assertEquals(jackOfClubs.value,jackOfDiamonds.value)
        assertNotEquals(aceOfSpades.value,jackOfDiamonds.value)
        assertNotEquals(sevenOfDiamonds.value,aceOfSpades.value)
    }
}