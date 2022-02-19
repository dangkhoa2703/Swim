package service

import entity.*

/**
 * TODO manage all the players action
 *
 * @property rootService
 */

class PlayerActionService(private val rootService: RootService) {

    private val currentGame = rootService.currentGame
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
        rootService.gameService.endTurn()
    }

    /**
     * TODO swap one card in player's hand with one in the middle stack
     *
     * @param playerCard the card in the player's, which the player chose to swap
     * @param middleCard the card in the middle stack, which the player chose to swap
     */
    fun swapOneCard(playerCard : PlayCard, middleCard : PlayCard) {
        val currentPlayer = rootService.gameService.currentPlayer
        checkNotNull(currentPlayer)
        rootService.gameService.resetPassCounter()
        currentPlayer.handCards.forEachIndexed { index, card ->
            if (gameService.compareTwoCards(card, playerCard) == 3) {
                currentPlayer.handCards[index] = middleCard
            }
        }

        currentGame?.middle?.forEachIndexed { index, card ->
            if (card == middleCard) {
                currentGame.middle[index] = playerCard
            }
        }
        gameService.endTurn()
    }

    /**
     * TODO when a player chose to pass, if four player consecutily passed, 3 new cards are deal to the middle stack
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
//                RefreshAfterPass()
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