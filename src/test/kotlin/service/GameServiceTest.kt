package service

import kotlin.test.*
import entity.*
import org.junit.jupiter.api.assertThrows

/**
 * TODO test the game service
 *
 */
class GameServiceTest {

    /**
     * create a full draw stack manually
     */
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

    /**
     * TODO test if after create new game, the number of player, cards in draw stack, cards in middle are correct
     *
     */
    @Test
    fun testStartNewGame(){
        val mc = RootService()
        val testRefreshable = TestRefreshable()
        mc.addRefreshable(testRefreshable)
        testRefreshable.reset()
        mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"))
        val fourPlayerGame = mc.currentGame

        checkNotNull(fourPlayerGame)
        assertEquals("p1",fourPlayerGame.names[0])
        assertEquals("p2",fourPlayerGame.names[1])
        assertEquals("p3",fourPlayerGame.names[2])
        assertEquals("p4",fourPlayerGame.names[3])
        assertEquals(4, fourPlayerGame.players.size)
        assertEquals(17, fourPlayerGame.drawStack.size)
        assertEquals(3, fourPlayerGame.middle.size)
        assertTrue(testRefreshable.refreshAfterStartNewGameCalled)


        mc.gameService.startNewGame(mutableListOf("p1","p2","p3"))
        val threePlayerGame = mc.currentGame
        checkNotNull(threePlayerGame)
        assertEquals(3, threePlayerGame.players.size)
        assertEquals(20, threePlayerGame.drawStack.size)
        assertEquals(3, threePlayerGame.middle.size)

        mc.gameService.startNewGame(mutableListOf("p1","p2"))
        val twoPlayerGame = mc.currentGame
        checkNotNull(twoPlayerGame)
        assertEquals(2, twoPlayerGame.players.size)
        assertEquals(23, twoPlayerGame.drawStack.size)
        assertEquals(3, twoPlayerGame.middle.size)


        testRefreshable.reset()
        assertThrows<IllegalArgumentException> {
            mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4","p5"))
            assertFalse(testRefreshable.refreshAfterStartNewGameCalled)
        }
        assertThrows<IllegalArgumentException> {
            mc.gameService.startNewGame(mutableListOf("p1"))
        }
    }

    /**
     * TODO test if the points are correctly calculated
     *
     */
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

        val cardList5 = mutableListOf(
            PlayCard(CardSuit.CLUBS, CardValue.SEVEN),
            PlayCard(CardSuit.DIAMONDS, CardValue.NINE),
            PlayCard(CardSuit.DIAMONDS, CardValue.QUEEN)
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

        mc.currentGame!!.players[3] = Player("p5",cardList5)
        assertEquals(19.0,mc.gameService.evaluatePlayCards(players[3]))

    }

    /**
     * TODO test if the PassCounter is set to 0 when calling function
     *
     */
    @Test
    fun testResetPassCounter(){
        val mc = RootService()
        mc.gameService.increasePassCounter()
        mc.gameService.increasePassCounter()
        mc.gameService.increasePassCounter()
        mc.gameService.resetPassCounter()
        assertEquals(0,mc.gameService.passCounter)
    }

    /**
     * TODO test if PassCounter is increase by 1 when calling function
     *
     */
    @Test
    fun testIncreasePassCounter(){
        val mc = RootService()
        mc.gameService.resetPassCounter()
        mc.gameService.increasePassCounter()
        mc.gameService.increasePassCounter()
        assertEquals(2,mc.gameService.passCounter)
    }

    /**
     * TODO test if the next player index is corrected calculated
     *
     */
    @Test
    fun testEndTurn(){
        val mc = RootService()
        val testRefreshable = TestRefreshable()
        mc.addRefreshable(testRefreshable)
        testRefreshable.reset()
        mc.gameService.startNewGame(mutableListOf("Harry","Ron","Hermione"))
        val currentGame = mc.currentGame
        checkNotNull(currentGame)
        val index = mc.gameService.currentPlayerIndex

        mc.gameService.endTurn()
        mc.gameService.endTurn()
        mc.gameService.endTurn()
        assertEquals(0,index)
        assertTrue(testRefreshable.refreshAfterEndTurnCalled)

        currentGame.players[1].hasKnock = true
        mc.gameService.endTurn()
        assertNotEquals(0.0,currentGame.players[1].score)

        testRefreshable.reset()
        assertThrows<IllegalStateException> {
            mc.currentGame = null
            mc.gameService.endTurn()
            assertFalse(testRefreshable.refreshAfterEndTurnCalled)
        }
    }

    /**
     * TODO test if the point of the player are correctly calculated and showd in correctly orders
     *
     */
    @Test
    fun testEndGame(){
        val mc = RootService()
        val testRefreshable = TestRefreshable()
        mc.addRefreshable(testRefreshable)
        testRefreshable.reset()

        mc.gameService.startNewGame(mutableListOf("p1","p2","p3","p4"))

        val temp: List<Player> = mc.gameService.endGame()
        assertEquals(4,temp.size)
        assertTrue(temp[0].score >= temp[1].score)
        assertTrue(temp[1].score >= temp[2].score)
        assertTrue(temp[2].score >= temp[3].score)
        assertTrue(testRefreshable.refreshAfterEndGameCalled)

        testRefreshable.reset()
        assertThrows<IllegalStateException> {
            mc.currentGame = null
            mc.gameService.endGame()
            assertFalse(testRefreshable.refreshAfterEndGameCalled)
        }
    }

    /**
     * TODO test if the compare method function correctly
     *
     */
    @Test
    fun testCompareCards() {
        val mc = RootService()
        val tempCard = PlayCard(CardSuit.CLUBS, CardValue.SEVEN)
        assertEquals(3,mc.gameService.compareTwoCards(cards[0],tempCard))
        assertEquals(2,mc.gameService.compareTwoCards(cards[0],cards[1]))
        assertEquals(1,mc.gameService.compareTwoCards(cards[0],cards[8]))
        assertEquals(0,mc.gameService.compareTwoCards(cards[0],cards[9]))
    }

    /**
     * TODO test if the draw stack have the needed cards and cards number
     *
     */
    @Test
    fun testCreateDrawStack(){
        val mc = RootService()
        val cardList = mc.gameService.createDrawStack()
        assertEquals(32,cardList.size)
//        cardList.forEach { ele ->
//            assertTrue(cards.contains(ele))
//        }
    }

    /**
     * TODO test if 3 cards are correctly dealed to the middle stack and player's hand
     *
     */
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


        assertThrows<IllegalArgumentException> {
            mc.currentGame?.drawStack = mutableListOf(cards[0],cards[1])
            mc.gameService.deal3Card()
        }

        assertThrows<IllegalStateException> {
            mc.currentGame = null
            mc.gameService.deal3Card()
        }
    }

}