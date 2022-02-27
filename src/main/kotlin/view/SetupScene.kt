package view

import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.components.uicomponents.*
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * TODO new game scene constructor
 *
 */

class SetupScene : MenuScene(400,1080), Refreshable {


    private val mainGrid: GridPane<GridPane<UIComponent>> = GridPane(200,540,columns = 1, rows = 3)
    private val swimGrid = GridPane<UIComponent>(columns = 1, rows = 1)
    private val playerGrid = GridPane<UIComponent>(columns = 2,rows = 4)
    private val buttonsGrid = GridPane<UIComponent>(columns = 3, rows = 1)

    private val swimFont = Font(100, fontWeight = Font.FontWeight.BOLD)
    private val titlesFont = Font(20, fontWeight = Font.FontWeight.BOLD)

    private val headLineLabel = Label(
        text = "SWIM",
        height = 300,
        width = 300,
        font = swimFont
    ).apply{

    }

//    private val p1Label: Label = Label(
//        width = 100, height = 35,
//        text = "Player 1:",
//        font = titlesFont
//    )

    val p1Input: TextField = TextField(
        width = 200, height = 35
    ).apply {
        onKeyTyped = {
            startButton.isDisabled =
                this.text.isBlank() || p2Input.text.isBlank() || p3Input.text.isBlank() || p4Input.text.isBlank()
        }
    }

//    private val p2Label = Label(
//        width = 100, height = 35,
//        text = "Player 2:",
//        font = titlesFont
//    )


    val p2Input: TextField = TextField(
        width = 200, height = 35
    ).apply {
        onKeyTyped = {
            startButton.isDisabled =
                p1Input.text.isBlank() && this.text.isBlank() && p3Input.text.isBlank() && p4Input.text.isBlank()
        }
    }


//    private val p3Label = Label(
//        width = 100, height = 35,
//        text = "Player 3:",
//        font = titlesFont
//    )

    val p3Input: TextField = TextField(
        width = 200, height = 35
    ).apply {
        onKeyTyped = {
            startButton.isDisabled =
                p1Input.text.isBlank() && p2Input.text.isBlank() && this.text.isBlank() && p4Input.text.isBlank()
        }
    }

//    private val p4Label = Label(
//        width = 100, height = 35,
//        text = "Player 4:",
//        font = titlesFont
//    )

    val p4Input: TextField = TextField(
        width = 200, height = 35
    ).apply {
        onKeyTyped = {
            startButton.isDisabled =
                p1Input.text.isBlank() && p2Input.text.isBlank() && p3Input.text.isBlank() && this.text.isBlank()
        }
    }

//    val labelsList = listOf(p1Label,p2Label,p3Label,p4Label)
private val labelsList: MutableList<Label> = mutableListOf()
    init{
        for(i in 0..3){
            val playerLabel = Label(
                width = 100, height = 35,
                text = "Player ${i+1}:",
                font = titlesFont
            )
            labelsList.add(playerLabel)
        }
    }
    private val inputsList = listOf(p1Input,p2Input,p3Input,p4Input)

    val startButton: Button = Button(
        width = 140, height = 35,
        text = "Start"
    ).apply {
        visual = ColorVisual(129, 178, 154)
    }

    val quitButton: Button = Button(
        width = 140, height = 35,
        text = "Quit"
    ).apply {
        visual = ColorVisual(224, 122, 95)
    }

    init{
        swimGrid[0,0] = headLineLabel
        mainGrid.setRowHeight(0,150)
        mainGrid[0,0] = swimGrid


        for(i in 0..3){
            playerGrid[0,i] = labelsList[i]
            playerGrid[1,i] = inputsList[i]
            playerGrid.setRowHeight(i,50)
        }
        playerGrid.setColumnWidth(0,100)
//        playerGrid.setColumnWidth(1,300)
        mainGrid[0,1] = playerGrid

        buttonsGrid[0,0] = startButton
        buttonsGrid[2,0] = quitButton
        buttonsGrid.setColumnWidth(1,50)
        buttonsGrid.setRowHeight(0,360)
        mainGrid[0,2] = buttonsGrid

//        for(i in 0..4){
//            mainGrid.setRowHeight(i,100)
//        }
//
//        mainGrid.setRowHeight(5,300)

        background = ColorVisual(244,241,222)

        addComponents(mainGrid)
    }


}