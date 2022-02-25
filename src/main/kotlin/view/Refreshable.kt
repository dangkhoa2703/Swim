package view

import entity.Player

/**
 * TODO list all the available refresh method
 *
 */

interface Refreshable {

    /**
     * TODO show the first player's cards and the middle stack
     *
     */
    fun refreshAfterStartNewGame() {}

    /**
     * TODO update the player's card and the middle stack after the player take an action
     *
     */
    fun refreshAfterPlayerAction() {}

    /**
     * TODO show the back of the next player's cards
     *
     */
    fun refreshAfterEndTurn() {}

    /**
     * TODO show the ranking of the players
     *
     */
    fun refreshAfterEndGame(players: List<Player>) {}



}