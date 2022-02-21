package service

import kotlin.test.*
import entity.*

class PlayerActionServiceTest {

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
    }

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
    }

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

        mc.playerActionService.swapOneCard(players[currentPlayerIndex].handCards[2],middle[0])
        assertTrue(30.0 == mc.gameService.evaluatePlayCards(players[currentPlayerIndex]))

    }

    @Test
    fun testPass(){
        val mc = RootService()
        mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"))
        val gameService = mc.gameService
        val playerActionService = mc.playerActionService

        gameService.resetPassCounter()
        playerActionService.pass()
        assertEquals(1,gameService.passCounter)
        playerActionService.pass()
        assertEquals(2,gameService.passCounter)
        playerActionService.pass()
        assertEquals(3,gameService.passCounter)
    }
}