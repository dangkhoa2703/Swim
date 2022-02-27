package service

import kotlin.test.*
import entity.*
import org.junit.jupiter.api.assertThrows

/**
 * TODO test all the player's action
 *
 */
class PlayerActionServiceTest {


    /**
     * TODO test if the passCounter has been reset and the next player are correctly calculated
     *
     */
    @Test
    fun testKnock(){
        val mc = RootService()
        mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"))
        val lastPlayerIndex = mc.gameService.currentPlayerIndex
        val players = mc.currentGame?.players

        checkNotNull(players)
        mc.playerActionService.knock()
        assertTrue(players[lastPlayerIndex].hasKnock)
        assertEquals(0,mc.gameService.passCounter)
        assertEquals((lastPlayerIndex + 1) % players.size,mc.gameService.currentPlayerIndex)
        assertThrows<IllegalStateException> {
            mc.gameService.currentPlayer = null
            mc.playerActionService.knock()
        }
    }

    /**
     * TODO test if after the swap the player have the middle stack and vice versa
     *
     */
    @Test
    fun testSwapAllCards(){
        val mc = RootService()
        mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"))
        val currentPlayerIndex = mc.gameService.currentPlayerIndex
        val players = mc.currentGame?.players
        val middle = mc.currentGame?.middle?.toMutableList()

        checkNotNull(players)
        checkNotNull(middle)
        mc.playerActionService.swapAllCards()
        assertEquals(0,mc.gameService.passCounter)
        for(i in 0..2){
            assertEquals(3,mc.gameService.compareTwoCards(players[currentPlayerIndex].handCards[i], middle[i]))
        }
        assertThrows<IllegalStateException> {
            mc.gameService.currentPlayer = null
            mc.playerActionService.swapAllCards()
        }
        assertThrows<IllegalStateException> {
            mc.currentGame = null
            mc.playerActionService.swapAllCards()
        }
    }

    /**
     * test if the card are correctly swapped
     */
    @Test
    fun testSwapOneCard(){
        val mc = RootService()
        mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"))
        val currentPlayerIndex = mc.gameService.currentPlayerIndex
        val players = mc.currentGame?.players
        val middle = mc.currentGame?.middle

        val cardList2 = mutableListOf(
            PlayCard(CardSuit.CLUBS, CardValue.TEN),
            PlayCard(CardSuit.DIAMONDS, CardValue.QUEEN),
            PlayCard(CardSuit.HEARTS, CardValue.QUEEN)
        )

        val cardList3 = mutableListOf(
            PlayCard(CardSuit.CLUBS, CardValue.QUEEN),
            PlayCard(CardSuit.CLUBS, CardValue.KING),
            PlayCard(CardSuit.DIAMONDS, CardValue.ACE)
        )
        checkNotNull(players)
        checkNotNull(middle)
        for(i in 0..2){
            players[currentPlayerIndex].handCards[i] = cardList3[i]
            middle[i] = cardList2[i]
        }

        mc.playerActionService.swapOneCard(2,0)
        assertTrue(30.0 == mc.gameService.evaluatePlayCards(players[currentPlayerIndex]))

        assertThrows<IllegalStateException> {
            mc.currentGame = null
            mc.playerActionService.swapOneCard(2,0)
        }
        assertThrows<IllegalStateException> {
            mc.gameService.currentPlayer = null
            mc.playerActionService.swapOneCard(2,0)
        }

    }

    /**
     * TODO test if the pass counter are correctly increased
     *
     */
    @Test
    fun testPass(){
        val mc = RootService()
        mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"))
        val gameService = mc.gameService
        val playerActionService = mc.playerActionService

        gameService.resetPassCounter()
        playerActionService.pass()
        assertEquals(1,gameService.passCounter)
        assertEquals(1,gameService.currentPlayerIndex)
        playerActionService.pass()
        assertEquals(2,gameService.passCounter)
        assertEquals(2,gameService.currentPlayerIndex)
        playerActionService.pass()
        assertEquals(3,gameService.passCounter)
        assertEquals(3,gameService.currentPlayerIndex)
        playerActionService.pass()
        assertEquals(0,gameService.passCounter)

        assertThrows<IllegalStateException> {
            mc.currentGame = null
            mc.playerActionService.pass()
        }

        mc.currentGame?.drawStack = mutableListOf(
            PlayCard(CardSuit.CLUBS, CardValue.SEVEN),
            PlayCard(CardSuit.CLUBS, CardValue.EIGHT)
        )
        mc.gameService.passCounter = 4
        mc.playerActionService.pass()
        assertNotEquals(0.0, mc.gameService.currentPlayer?.score)
    }
}