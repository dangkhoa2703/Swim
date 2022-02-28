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
        rootService.gameService.passCounter = 0
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
        rootService.gameService.passCounter = 0
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
        val currentPlayer = rootService.gameService.currentPlayer
        val currentGame = rootService.currentGame
        checkNotNull(currentPlayer)
        checkNotNull(currentGame)
        rootService.gameService.passCounter = 0

        val playerCardSuit = currentPlayer.handCards[playerCardIndex].suitEnum
        val playerCardValue = currentPlayer.handCards[playerCardIndex].valueEnum

        currentPlayer.handCards[playerCardIndex] = PlayCard(
            currentGame.middle[middleCardIndex].suitEnum,
            currentGame.middle[middleCardIndex].valueEnum
        )
        currentGame.middle[middleCardIndex] = PlayCard(playerCardSuit, playerCardValue)

        onAllRefreshables { refreshAfterPlayerAction() }
    }

    /**
     * TODO when a player chose to pass, if four player consecutively passed, 3 new cards are deal to the middle stack
     *
     */
    fun pass() {
        // retrieve current game from root service
        val game = rootService.currentGame
        checkNotNull(game)
        //increment pass counter
        rootService.gameService.passCounter++
        //check if all players chose to pass
        if(gameService.passCounter == game.players.size)
        {
            //test if enough cards are left to change middle cards
            if(game.drawStack.size >= 3)
            {
                //change middle stack, reset pass counter and refresh scene
                game.middle = gameService.deal3Card().toMutableList()
                rootService.gameService.passCounter = 0
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