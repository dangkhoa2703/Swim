package entity

import kotlin.test.*

class SwimTest {
    private val aceOfSpades = PlayCard(CardSuit.SPADES, CardValue.ACE)
    private val jackOfClubs = PlayCard(CardSuit.CLUBS, CardValue.JACK)
    private val queenOfHearts = PlayCard(CardSuit.HEARTS, CardValue.QUEEN)
    private val kingOfHearts = PlayCard(CardSuit.HEARTS, CardValue.KING)
    private val tenOfSpades = PlayCard(CardSuit.SPADES, CardValue.TEN)
    private val nineOfClubs = PlayCard(CardSuit.CLUBS, CardValue.NINE)
    private val fiveOfDiamonds = PlayCard(CardSuit.DIAMONDS, CardValue.FIVE)
    private val eightOfSpades = PlayCard(CardSuit.SPADES, CardValue.EIGHT)
    private val sevenOfHearts = PlayCard(CardSuit.HEARTS, CardValue.SEVEN)
    private val twoOfSpades = PlayCard(CardSuit.SPADES, CardValue.TWO)
    private val aceOfDiamonds = PlayCard(CardSuit.DIAMONDS, CardValue.ACE)
    private val player1 = Player("Player1")
    private val cardListMiddle = listOf(aceOfSpades,jackOfClubs,queenOfHearts)
    private val cardListCardDeck = listOf(kingOfHearts,tenOfSpades,nineOfClubs,fiveOfDiamonds,eightOfSpades,sevenOfHearts,twoOfSpades,aceOfDiamonds)
    private val middleStack = Middle(cardListMiddle)
    private val newGame = Swim(middleStack,cardListCardDeck,player1)

}