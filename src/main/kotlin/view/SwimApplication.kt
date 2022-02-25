package view

import entity.Player
import service.*
import tools.aqua.bgw.core.BoardGameApplication

class SwimApplication : BoardGameApplication("SWIM"), Refreshable {

    private val rootService = RootService()

    private val gameScene = GameScene(rootService)


    private val setupScene = SetupScene(rootService).apply {
        quitButton.onMouseClicked = {
            exit()
        }
        startButton.onMouseClicked = {
            for(playerInput in playerInputs){
                if(!playerInput.text.isBlank()){
                    players.add(playerInput.text)
                }
            }
            rootService.gameService.startNewGame(players)
            val players = rootService.currentGame?.players
            checkNotNull(players)
            gameScene.currentPlayerName.text = players[0].name
            gameScene.nextPlayerName.text = players[1].name
        }
    }

    init{
        rootService.addRefreshables(
            this,
            setupScene,
            gameScene,
        )
        this.showGameScene(gameScene)
//            val gameScene = GameScene(rootService)
//            rootService.addRefreshables(gameScene)
//            this@SwimApplication.showGameScene(gameScene)
//        }
        this.showMenuScene(setupScene, 0)
    }

    override fun refreshAfterStartNewGame() {
        this.hideMenuScene()
    }

    override fun refreshAfterEndGame(players: List<Player>) {
        val finishScene = GameFinishScene(players).apply {
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