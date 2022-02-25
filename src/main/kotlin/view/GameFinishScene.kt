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

    val grid = GridPane<UIComponent>(200,540,columns = 2, rows = 5)

    val newGameButton = Button(width = 140, height = 35, posX = 210, posY = 265, text = "New Game").apply {
        visual = ColorVisual(Color(129,178,154))
    }

    val quitButton = Button(width = 140, height = 35, posX = 50, posY = 265, text = "Quit").apply {
        visual = ColorVisual(Color(224,122,95))
    }

    init{
        for(i in players.indices){
            val playerLabel = Label(
                text = "${i + 1}.Place"
            )
            val playerName = Label(
                text = players[i].name
            )
            grid[0,i] = playerLabel
            grid[1,i] = playerName
        }
        grid[0,4] = newGameButton
        grid[1,4] = quitButton

        background = ColorVisual(244,241,222)

        addComponents((grid))
    }

}