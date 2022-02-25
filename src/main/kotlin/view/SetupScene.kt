package view

import service.*
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.components.uicomponents.*
import tools.aqua.bgw.visual.ColorVisual



class SetupScene(private val rootService: RootService) : MenuScene(400,1080), Refreshable {


    private val grid: GridPane<UIComponent> = GridPane<UIComponent>(200,540,columns = 2, rows = 6)

    private val headLineLabel = Label(
        text = "SWIM",
        height = 100,
        width = 100
    )

    private val p1Label: Label = Label(
        width = 100, height = 35,
        text = "Player 1:"
    )

    private val p1Input: TextField = TextField(
        width = 200, height = 35
    ).apply {
        onKeyTyped = {
            startButton.isDisabled =
                this.text.isBlank() || p2Input.text.isBlank() || p3Input.text.isBlank() || p4Input.text.isBlank()
        }
    }

    private val p2Label = Label(
        width = 100, height = 35,
        text = "Player 2:"
    )


    private val p2Input: TextField = TextField(
        width = 200, height = 35
    ).apply {
        onKeyTyped = {
            startButton.isDisabled =
                p1Input.text.isBlank() && this.text.isBlank() && p3Input.text.isBlank() && p4Input.text.isBlank()
        }
    }


    private val p3Label = Label(
        width = 100, height = 35,
        text = "Player 3:"
    )

    private val p3Input: TextField = TextField(
        width = 200, height = 35
    ).apply {
        onKeyTyped = {
            startButton.isDisabled =
                p1Input.text.isBlank() && p2Input.text.isBlank() && this.text.isBlank() && p4Input.text.isBlank()
        }
    }

    private val p4Label = Label(
        width = 100, height = 35,
        text = "Player 4:"
    )

    private val p4Input: TextField = TextField(
        width = 200, height = 35
    ).apply {
        onKeyTyped = {
            startButton.isDisabled =
                p1Input.text.isBlank() && p2Input.text.isBlank() && p3Input.text.isBlank() && this.text.isBlank()
        }
    }

    val playerInputs = listOf(p1Input,p2Input,p3Input,p4Input)

    val players = mutableListOf<String>()


    val startButton: Button = Button(
        width = 140, height = 35,
        text = "Start"
    ).apply {
        visual = ColorVisual(129, 178, 154)
//        onMouseClicked = {
//            for(playerInput in playerInputs){
//                if(!playerInput.text.isBlank()){
//                    players.add(playerInput.text)
//                }
//            }
//            rootService.gameService.startNewGame(players)
//        }
    }

    val quitButton: Button = Button(
        width = 140, height = 35,
        text = "Quit"
    ).apply {
        visual = ColorVisual(224, 122, 95)
    }

    init{
        grid[0,0] = p1Label; grid[1,0] = p1Input
        grid[0,1] = p2Label; grid[1,1] = p2Input
        grid[0,2] = p3Label; grid[1,2] = p3Input
        grid[0,3] = p4Label; grid[1,3] = p4Input

        grid[0,5] = startButton
        grid[1,5] = quitButton

        grid.setRowHeight(0,100)
        grid.setRowHeight(1,100)
        grid.setRowHeight(2,100)
        grid.setRowHeight(3,100)
        grid.setRowHeight(4,400)

        background = ColorVisual(244,241,222)

        addComponents(grid)
    }


}