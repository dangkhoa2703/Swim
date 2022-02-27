package service

import view.Refreshable
import entity.Player

/**
 * [Refreshable] implementation that refreshes nothing, but remembers
 * if a refresh method has been called (since last [reset])
 */
class TestRefreshable: Refreshable {

    var refreshAfterStartNewGameCalled = false
        private set

    var refreshAfterPlayerActionCalled = false
        private set

    var refreshAfterEndTurnCalled = false
        private set

    var refreshAfterEndGameCalled = false
        private set

    /**
     * resets all *Called properties to false
     */
    fun reset() {
        refreshAfterStartNewGameCalled = false
        refreshAfterPlayerActionCalled = false
        refreshAfterEndTurnCalled = false
        refreshAfterEndGameCalled = false
    }

    override fun refreshAfterStartNewGame() {
        refreshAfterStartNewGameCalled = true
    }

    override fun refreshAfterPlayerAction() {
        refreshAfterPlayerActionCalled = true
    }

    override fun refreshAfterEndTurn() {
        refreshAfterEndTurnCalled = true
    }

    override fun refreshAfterEndGame(players: List<Player>) {
        refreshAfterEndGameCalled = true
    }
}