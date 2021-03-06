package view

import entity.Player
import service.*
import tools.aqua.bgw.core.BoardGameApplication

/**
 * TODO manage the switching between scene
 *
 */

class SwimApplication : BoardGameApplication("SWIM"), Refreshable {

    private val rootService = RootService()

    private val gameScene = GameScene(rootService)


    private val setupScene = SetupScene().apply {
        quitButton.onMouseClicked = {
            exit()
        }
        startButton.onMouseClicked = {
            resetGame()
            val players: MutableList<String> = mutableListOf()
            players.add(p1Input.text)
            players.add(p2Input.text)
            players.add(p3Input.text)
            players.add(p4Input.text)
            players.removeIf { name -> name.isBlank() }
            rootService.gameService.startNewGame(players)
            val game = rootService.currentGame
            checkNotNull(game)
        }
    }

    /**
     * TODO reset current game, passCounter, currentPlayer and currentPlayerIndex
     *
     */
    private fun resetGame(){
        rootService.currentGame = null
        rootService.gameService.passCounter = 0
        rootService.gameService.currentPlayer = null
        rootService.gameService.currentPlayerIndex = 0
    }

    init{
        rootService.addRefreshables(
            this,
            setupScene,
            gameScene,
        )
        this.showGameScene(gameScene)
        this.showMenuScene(setupScene, 0)
    }

    override fun refreshAfterStartNewGame() {
        this.hideMenuScene()
    }

    override fun refreshAfterEndGame(players: List<Player>) {
        val finishScene = GameFinishScene(players).apply {
            gameScene.resetGameScene()
            newGameButton.onMouseClicked = {
                this@SwimApplication.showMenuScene(setupScene)
            }
            quitButton.onMouseClicked = {
                exit()
            }
        }
        this.showMenuScene(finishScene)
    }
}