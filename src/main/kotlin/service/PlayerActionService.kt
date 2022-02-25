package service

import entity.*

/**
 * TODO manage all the players action
 *
 * @property rootService
 */

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {

    private val gameService = rootService.gameService

    /**
     * TODO player chose knock
     *
     */
    fun knock(){
        val currentPlayer = rootService.gameService.currentPlayer
        checkNotNull(currentPlayer)
        currentPlayer.hasKnock = true
        rootService.gameService.resetPassCounter()
        rootService.gameService.endTurn()
    }

    /**
     * TODO swap all cards in player's hand with the middle stack
     *
     */
    fun swapAllCards() {
        val currentGame = rootService.currentGame
        val currentPlayer = rootService.gameService.currentPlayer
        checkNotNull(currentGame)
        checkNotNull(currentPlayer)
        rootService.gameService.resetPassCounter()
        val tempHandCards = currentPlayer.handCards.toMutableList()
        val tempMiddle = currentGame.middle.toMutableList()
        for(i in 0..2){
            currentGame.middle[i] = tempHandCards[i]
            currentPlayer.handCards[i] = tempMiddle[i]
        }
        onAllRefreshables { refreshAfterPlayerAction() }
    }

    /**
     * TODO swap one card in player's hand with one in the middle stack
     *
     * @param playerCardIndex index of card in the player's, which the player chose to swap
     * @param middleCardIndex index of card in the middle stack, which the player chose to swap
     */
    fun swapOneCard(playerCardIndex : Int, middleCardIndex : Int) {
        val handCards = rootService.gameService.currentPlayer?.handCards
        val middle = rootService.currentGame?.middle
        checkNotNull(handCards)
        checkNotNull(middle)
        rootService.gameService.resetPassCounter()

        val playerCardSuit = handCards[playerCardIndex].suitEnum
        val playerCardValue = handCards[playerCardIndex].valueEnum

        handCards[playerCardIndex] = PlayCard(middle[middleCardIndex].suitEnum, middle[middleCardIndex].valueEnum)
        middle[middleCardIndex] = PlayCard(playerCardSuit, playerCardValue)

//        currentPlayer.handCards.forEachIndexed { index, card ->
//            if (gameService.compareTwoCards(card, playerCard) == 3) {
//                val temp = currentPlayer.handCards[index]
//                currentPlayer.handCards[index] = middleCard
//            }
//        }
//
//        currentGame?.middle?.forEachIndexed { index, card ->
//            if (card == middleCard) {
//                currentGame.middle[index] =
//            }
//        }
        onAllRefreshables { refreshAfterPlayerAction() }
    }

    /**
     * TODO when a player chose to pass, if four player consecutively passed, 3 new cards are deal to the middle stack
     *
     */
    fun pass() {
        // retrieve current game from root service
        val currentGame = rootService.currentGame
        val drawStack = currentGame?.drawStack
        checkNotNull(currentGame)
        checkNotNull(drawStack)
        //increment pass counter
        rootService.gameService.increasePassCounter()
        //check if all players chose to pass
        if(gameService.passCounter == currentGame.players.size)
        {
            //test if enough cards are left to change middle cards
            if(drawStack.size >= 3)
            {
                //change middle stack, reset pass counter and refresh scene
                currentGame.middle = gameService.deal3Card().toMutableList()
                rootService.gameService.resetPassCounter()
                onAllRefreshables { refreshAfterPlayerAction() }
            }
            //end game and refresh scene
            else
            {
                rootService.gameService.endGame()
//                RefreshAfterGameEnd()
                return
            }
        }
        //else do nothing and end Turn
        rootService.gameService.endTurn()

    }

}