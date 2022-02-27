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
    private val twoOfClubs = PlayCard(CardSuit.DIAMONDS, CardValue.TWO)
    private val threeOfClubs = PlayCard(CardSuit.DIAMONDS, CardValue.THREE)
    private val fourOfClubs = PlayCard(CardSuit.DIAMONDS, CardValue.FOUR)
    private val fiveOfClubs = PlayCard(CardSuit.DIAMONDS, CardValue.FIVE)
    private val sixOfClubs = PlayCard(CardSuit.DIAMONDS, CardValue.SIX)


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
        assertEquals(2.0,twoOfClubs.value)
        assertEquals(3.0,threeOfClubs.value)
        assertEquals(4.0,fourOfClubs.value)
        assertEquals(5.0,fiveOfClubs.value)
        assertEquals(6.0,sixOfClubs.value)
    }

    /**
     * TODO test shortDeck function in cardValue
     *
     */
    @Test
    fun testShortDeck(){
        val shortDeck = CardValue.shortDeck()
        assertEquals(8, shortDeck.size)
    }

    @Test
    fun testToStringValue(){
        assertEquals("2",CardValue.TWO.toString())
        assertEquals("3",CardValue.THREE.toString())
        assertEquals("4",CardValue.FOUR.toString())
        assertEquals("5",CardValue.FIVE.toString())
        assertEquals("6",CardValue.SIX.toString())

    }
}