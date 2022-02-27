package view

import entity.Player
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * TODO finish scene constructor
 *
 * @property players
 */
class GameFinishScene(val players: List<Player>) : MenuScene(400,1080), Refreshable {

    private val mainGrid = GridPane<GridPane<UIComponent>>(200,540,1,2)
    private val playersGrid = GridPane<UIComponent>(200,360,3,6)
    private val buttonsGrid = GridPane<UIComponent>(200,900,3,1)

    private val ranksLabel = Label( text = "RANK")
    private val playersNamesLabel = Label( text = "NAME")
    private val scoreLabel = Label( text = "SCORE")

    val newGameButton = Button(width = 140, height = 35, text = "New Game").apply {
        visual = ColorVisual(Color(129,178,154))
    }

    val quitButton = Button(width = 140, height = 35, text = "Quit").apply {
        visual = ColorVisual(Color(224,122,95))
    }

    init{
        for(i in players.indices){
            val playerLabel = Label(
                text = "${i + 1}."
            )
            val playerName = Label(
                text = players[i].name
            )
            val playerScore = Label(
                text = players[i].score.toString()
            )
            playersGrid[0,i + 2] = playerLabel
            playersGrid[1,i + 2] = playerName
            playersGrid[2,i + 2] = playerScore
            playersGrid.setRowHeight(i+2,50)
        }
        playersGrid[0,1] = ranksLabel
        playersGrid[1,1] = playersNamesLabel
        playersGrid[2,1] = scoreLabel
        playersGrid.setRowHeight(0,150)
        playersGrid.setRowHeight(1,80)

        buttonsGrid[0,0] = newGameButton
        buttonsGrid[2,0] = quitButton
        buttonsGrid.setRowHeight(0,360)
        buttonsGrid.setColumnWidth(1,50)

        mainGrid[0,0] = playersGrid
        mainGrid[0,1] = buttonsGrid

        background = ColorVisual(244,241,222)

        addComponents((mainGrid))
    }

}