package service

import kotlin.test.*
import entity.*

class GameServiceTest {

    private val cards = listOf(
        PlayCard(CardSuit.CLUBS, CardValue.SEVEN),
        PlayCard(CardSuit.CLUBS, CardValue.EIGHT),
        PlayCard(CardSuit.CLUBS, CardValue.NINE),
        PlayCard(CardSuit.CLUBS, CardValue.TEN),
        PlayCard(CardSuit.CLUBS, CardValue.JACK),
        PlayCard(CardSuit.CLUBS, CardValue.QUEEN),
        PlayCard(CardSuit.CLUBS, CardValue.KING),
        PlayCard(CardSuit.CLUBS, CardValue.ACE),
        PlayCard(CardSuit.HEARTS, CardValue.SEVEN),
        PlayCard(CardSuit.HEARTS, CardValue.EIGHT),
        PlayCard(CardSuit.HEARTS, CardValue.NINE),
        PlayCard(CardSuit.HEARTS, CardValue.TEN),
        PlayCard(CardSuit.HEARTS, CardValue.JACK),
        PlayCard(CardSuit.HEARTS, CardValue.QUEEN),
        PlayCard(CardSuit.HEARTS, CardValue.KING),
        PlayCard(CardSuit.HEARTS, CardValue.QUEEN),
        PlayCard(CardSuit.SPADES, CardValue.SEVEN),
        PlayCard(CardSuit.SPADES, CardValue.EIGHT),
        PlayCard(CardSuit.SPADES, CardValue.NINE),
        PlayCard(CardSuit.SPADES, CardValue.TEN),
        PlayCard(CardSuit.SPADES, CardValue.JACK),
        PlayCard(CardSuit.SPADES, CardValue.ACE),
        PlayCard(CardSuit.SPADES, CardValue.KING),
        PlayCard(CardSuit.SPADES, CardValue.ACE),
        PlayCard(CardSuit.DIAMONDS, CardValue.SEVEN),
        PlayCard(CardSuit.DIAMONDS, CardValue.EIGHT),
        PlayCard(CardSuit.DIAMONDS, CardValue.NINE),
        PlayCard(CardSuit.DIAMONDS, CardValue.TEN),
        PlayCard(CardSuit.DIAMONDS, CardValue.JACK),
        PlayCard(CardSuit.DIAMONDS, CardValue.KING),
        PlayCard(CardSuit.DIAMONDS, CardValue.QUEEN),
        PlayCard(CardSuit.DIAMONDS, CardValue.ACE)
    )
    @Test
    fun testStartNewGame(){
        val mc = RootService()
        mc.gameService.startNewGame(mutableListOf("Harry","Ron","Hermione"))
        val currentGame = mc.currentGame
        checkNotNull(mc.currentGame)
        assertEquals(3, currentGame!!.players.size)
        assertEquals(20, currentGame.drawStack.size)
        assertEquals(3, currentGame.middle.size)
    }

    @Test
    fun testEvaluateCards(){
        val mc = RootService()
        mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"))
        //all three cards have same suit
        val cardList1 = mutableListOf(
            PlayCard(CardSuit.CLUBS, CardValue.QUEEN),
            PlayCard(CardSuit.CLUBS, CardValue.KING),
            PlayCard(CardSuit.CLUBS, CardValue.ACE)
        )
        //all three cards have the same value
        val cardList2 = mutableListOf(
            PlayCard(CardSuit.CLUBS, CardValue.QUEEN),
            PlayCard(CardSuit.DIAMONDS, CardValue.QUEEN),
            PlayCard(CardSuit.HEARTS, CardValue.QUEEN)
        )
        //two of three cards have the same suit
        val cardList3 = mutableListOf(
            PlayCard(CardSuit.CLUBS, CardValue.QUEEN),
            PlayCard(CardSuit.CLUBS, CardValue.KING),
            PlayCard(CardSuit.DIAMONDS, CardValue.ACE)
        )
        //none of the cards have the same suit
        val cardList4 = mutableListOf(
            PlayCard(CardSuit.CLUBS, CardValue.SEVEN),
            PlayCard(CardSuit.DIAMONDS, CardValue.NINE),
            PlayCard(CardSuit.SPADES, CardValue.EIGHT)
        )

        val players = mc.currentGame?.players
        checkNotNull(players)

        mc.currentGame!!.players[0] = Player("p1",cardList1)
        mc.currentGame!!.players[1] = Player("p2",cardList2)
        mc.currentGame!!.players[2] = Player("p3",cardList3)
        mc.currentGame!!.players[3] = Player("p4",cardList4)

        assertEquals(30.5,mc.gameService.evaluatePlayCards(players[1]))
        assertEquals(20.0,mc.gameService.evaluatePlayCards(players[2]))
        assertEquals(9.0,mc.gameService.evaluatePlayCards(players[3]))
        assertEquals(31.0,mc.gameService.evaluatePlayCards(players[0]))

    }

    @Test
    fun testResetPassCounter(){
        val mc = RootService()
        mc.gameService.increasePassCounter()
        mc.gameService.increasePassCounter()
        mc.gameService.increasePassCounter()
        mc.gameService.resetPassCounter()
        assertEquals(0,mc.gameService.passCounter)
    }

    @Test
    fun testIncreasePassCounter(){
        val mc = RootService()
        mc.gameService.resetPassCounter()
        mc.gameService.increasePassCounter()
        mc.gameService.increasePassCounter()
        assertEquals(2,mc.gameService.passCounter)
    }

    @Test
    fun testEndTurn(){
        val mc = RootService()
        mc.gameService.startNewGame(mutableListOf("Harry","Ron","Hermione"))
        val currentGame = mc.currentGame
        checkNotNull(mc.currentGame)
        mc.gameService.endTurn()
        mc.gameService.endTurn()
        val index = mc.gameService.currentPlayerIndex
        if (currentGame != null) {
            assertEquals(0,index)
        }
    }

    @Test
    fun testEndGame(){
        val mc = RootService()

        mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"))

        val temp: List<Player> = mc.gameService.endGame()
        assertEquals(4,temp.size)
        assertTrue(temp[0].score > temp[1].score)
        assertTrue(temp[1].score > temp[2].score)
        assertTrue(temp[2].score > temp[3].score)
    }

    @Test
    fun testCompareCards() {
        val mc = RootService()
        val tempCard = PlayCard(CardSuit.CLUBS, CardValue.SEVEN)
        assertEquals(3,mc.gameService.compareTwoCards(cards[0],tempCard))
        assertEquals(2,mc.gameService.compareTwoCards(cards[0],cards[1]))
        assertEquals(1,mc.gameService.compareTwoCards(cards[0],cards[8]))
        assertEquals(0,mc.gameService.compareTwoCards(cards[0],cards[9]))
    }

    @Test
    fun testCreateDrawStack(){
        val mc = RootService()
        val cardList = mc.gameService.createDrawStack()
        cardList.forEach { ele ->
            assertTrue(cards.contains(ele))
        }
    }

    @Test
    fun testDeal3Card(){
        val mc = RootService()
        mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"))
        val drawStack = mc.currentGame?.drawStack
        checkNotNull(drawStack)
        val sizeBeforeDeal = drawStack.size
        val tempList = mc.gameService.deal3Card()
        assertEquals(3, tempList.size)
        assertTrue(sizeBeforeDeal > drawStack.size)
    }

}