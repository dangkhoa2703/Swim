package entity

import org.junit.jupiter.api.assertThrows
import kotlin.test.*

/**
 * TODO test the player's installation
 *
 */
class PlayerTest {
    private val aceOfSpades = PlayCard(CardSuit.SPADES, CardValue.ACE)
    private val jackOfClubs = PlayCard(CardSuit.CLUBS, CardValue.JACK)
    private val jackOfDiamonds = PlayCard(CardSuit.DIAMONDS, CardValue.JACK)
    private val cardList1 = mutableListOf(aceOfSpades,jackOfClubs,jackOfDiamonds)
    private val cardList2 = mutableListOf(aceOfSpades,jackOfClubs,jackOfDiamonds)
    private val player1 = Player("Player1",cardList1)

    /**
     * TODO test player construction
     *
     */
    @Test
    fun checkPlayerConstructor(){
        assertEquals(player1.name , "Player1")
        assertTrue(player1.name.length in 1..15)
        assertThrows<IllegalArgumentException> { Player("",cardList2) }
        assertThrows<IllegalArgumentException> { Player("12345678910111213141516", cardList2) }
    }
}