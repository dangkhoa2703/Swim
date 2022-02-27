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
    private val fourOfClubs = PlayCard(CardSuit.DIAMONDS, CardValue.FOUR)
    private val fiveOfClubs = PlayCard(CardSuit.DIAMONDS, CardValue.FIVE)
    private val sixOfClubs = PlayCard(CardSuit.DIAMONDS, CardValue.SIX)
    private val cardList1 = mutableListOf(aceOfSpades,jackOfClubs,jackOfDiamonds)
    private val cardList2 = mutableListOf(fourOfClubs,fiveOfClubs,sixOfClubs)
    private val player1 = Player("Player1",cardList1)

    /**
     * TODO test player construction
     *
     */
    @Test
    fun checkPlayerConstructor(){
        assertEquals(player1.name , "Player1")
        assertTrue(player1.name.length in 1..15)
        player1.handCards = cardList2
        assertEquals("4",cardList2[0].valueEnum.toString())
        assertEquals("5",cardList2[1].valueEnum.toString())
        assertEquals("6",cardList2[2].valueEnum.toString())
        assertThrows<IllegalArgumentException> { Player("",cardList2) }
        assertThrows<IllegalArgumentException> { Player("12345678910111213141516", cardList2) }
    }
}