package service

import view.Refreshable
import entity.Player

/**
 * TODO [Refreshable] implementation that refreshes nothing, but remembers
 *  if a refresh method has been called (since last [reset])
 *
 */
class TestRefreshable: Refreshable {

    /**
     *  remember if a refreshAfterStartNewGame was called
     */
    var refreshAfterStartNewGameCalled = false
        private set


    /**
     *  remember if a refreshAfterPlayerActionCalled was called
     */
    var refreshAfterPlayerActionCalled = false
        private set


    /**
     *  remember if a refreshAfterEndTurnCalled was called
     */
    var refreshAfterEndTurnCalled = false
        private set


    /**
     *  remember if a refreshAfterEndGame was called
     */
    var refreshAfterEndGameCalled = false
        private set

    /**
     * TODO reset all refresh variable to false
     *
     */
    fun reset() {
        refreshAfterStartNewGameCalled = false
        refreshAfterPlayerActionCalled = false
        refreshAfterEndTurnCalled = false
        refreshAfterEndGameCalled = false
    }

    /**
     * TODO test refreshAfterStartNewGame
     *
     */
    override fun refreshAfterStartNewGame() {
        refreshAfterStartNewGameCalled = true
    }

    /**
     * TODO test refreshAfterPlayerAction
     *
     */
    override fun refreshAfterPlayerAction() {
        refreshAfterPlayerActionCalled = true
    }

    /**
     * TODO test refreshAfterEndTurn
     *
     */
    override fun refreshAfterEndTurn() {
        refreshAfterEndTurnCalled = true
    }

    /**
     * TODO test refreshAfterEndGame
     *
     */
    override fun refreshAfterEndGame(players: List<Player>) {
        refreshAfterEndGameCalled = true
    }
}