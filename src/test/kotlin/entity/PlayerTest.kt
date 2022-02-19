package entity

import kotlin.test.*

class PlayerTest {
    private val aceOfSpades = PlayCard(CardSuit.SPADES, CardValue.ACE)
    private val jackOfClubs = PlayCard(CardSuit.CLUBS, CardValue.JACK)
    private val jackOfDiamonds = PlayCard(CardSuit.DIAMONDS, CardValue.JACK)
    private val kingOfDiamonds = PlayCard(CardSuit.DIAMONDS, CardValue.KING)
    private val cardList = mutableListOf<PlayCard>(aceOfSpades,jackOfClubs,jackOfDiamonds)
    private val player1 = Player("Player1",cardList)

    @Test
    fun ValidNameTest(){
        assertTrue(player1.name.length in 1..30)
    }

    @Test
    fun CheckPlayerConstructor(){
        assertEquals(player1.name , "Player1")
    }
}