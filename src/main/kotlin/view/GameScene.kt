package view

import entity.CardImageLoader
import entity.PlayCard
import service.RootService
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.components.uicomponents.*
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.ComponentView
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.visual.ColorVisual

/**
 * TODO game scene constructor
 *
 * @property rootService
 */

class GameScene(private val rootService: RootService) : BoardGameScene(1920,1080), Refreshable {

    private var playerCardIndex = 4
    private var middleCardIndex = 4
    private var playerHasNotTookAction = true
    private var hasSomeOneKnocked = false

    private val mainGrid = GridPane<ComponentView>(960,540,columns = 5, rows = 3)
    private val buttonGrid = GridPane<ComponentView>(225,830, columns = 1, rows = 4)
    private val nextPlayerGrid = GridPane<ComponentView>(1695,250, columns = 1, rows = 5)
    private val rightSideButtonGrid = GridPane<ComponentView>(1695,830,columns = 1, rows = 2)

    private val drawStack = LabeledStackView(label = "draw stack")

    private val playersNameFont = Font(20)
    private val titlesFont = Font(20, fontWeight = Font.FontWeight.BOLD)

    /**
     * create container for middle stack and player's cards
     */
    private val middle1 = LabeledStackView(label = "middle first card").apply{
        onMouseClicked = {
            middleCardIndex = 0
            onSelectedPlayersCards(1,0,"middle")
        }
    }

    private val middle2 = LabeledStackView(label = "middle second card").apply{
        onMouseClicked = {
            middleCardIndex = 1
            onSelectedPlayersCards(2,0,"middle")
        }
    }
    private val middle3 = LabeledStackView(label = "middle third card").apply{
        onMouseClicked = {
            middleCardIndex = 2
            onSelectedPlayersCards(3,0,"middle")
        }
    }

    private val middleStackLabeledStackView = listOf(middle1,middle2,middle3)

    private val playersFirstCard = LabeledStackView(label = "player's first card").apply{
        onMouseClicked = {
            playerCardIndex = 0
            onSelectedPlayersCards(1,2,"player")
        }

    }
    private val playersSecondCard = LabeledStackView(label = "player's second card").apply{
        onMouseClicked = {
            playerCardIndex = 1
            onSelectedPlayersCards(2,2,"player")
        }
    }
    private val playersThirdCard = LabeledStackView(label = "player's third card").apply{
        onMouseClicked = {
            playerCardIndex = 2
            onSelectedPlayersCards(3,2,"player")
        }
    }

    private val playersCardLabeledStackView = listOf(playersFirstCard,playersSecondCard,playersThirdCard)

    /*-----------------------------BUTTON-----------------------------*/

    private val knockButton = Button(
        width = 140, height = 35,
        text = "KNOCK!!!"
    ).apply {
        visual = ColorVisual(242,204,143)
        onMouseClicked = {
            val playersName = rootService.gameService.currentPlayer?.name
            checkNotNull(playersName)

            this.isDisabled = true
            knockedPlayerName.text = playersName
            hasSomeOneKnocked = true

            rootService.playerActionService.knock()
        }
    }

    //PASS BUTTON
    private val passButton = Button(
        width = 140, height = 35,
        text = "Pass"
    ).apply {
        visual = ColorVisual(242,204,143)
        onMouseClicked = {
            rootService.playerActionService.pass()
        }
    }

    //SWAP ONE CARD BUTTON
    private val swapOneCardButton = Button(
        width = 140, height = 35,
        text = "Change one card"
    ).apply {
        visual = ColorVisual(242,204,143)
        onMouseClicked = {
            val currentPlayerCard = rootService.gameService.currentPlayer?.handCards
            val middle = rootService.currentGame?.middle
            checkNotNull(currentPlayerCard){"game was not initialized"}
            checkNotNull(middle){"game was not initialized"}
            rootService.playerActionService.swapOneCard(playerCardIndex, middleCardIndex)

            mainGrid.setRowCenterMode(0,Alignment.CENTER)
            mainGrid.setRowCenterMode(2,Alignment.CENTER)

            knockButton.isDisabled = true
            passButton.isDisabled = true
            this.isDisabled = true
            swapAllCardButton.isDisabled = true

            endTurnButton.isDisabled = false
        }
    }

    //SWAP ALL CARD BUTTON
    private val swapAllCardButton: Button = Button(
        width = 140, height = 35,
        text = "Change all card"
    ).apply {
        visual = ColorVisual(242,204,143)
        onMouseClicked = {
            rootService.playerActionService.swapAllCards()

            knockButton.isDisabled = true
            passButton.isDisabled = true
            swapOneCardButton.isDisabled = true
            this.isDisabled = true

            endTurnButton.isDisabled = false
        }
    }

    //FLIP CARD BUTTON
    private val flipCardButton = Button(
        width = 140, height = 35,
        text = "Flip card"
    ).apply {
        visual = ColorVisual(242,204,143)
        onMouseClicked = {
            playersCardLabeledStackView.forEach { cardView ->
                when (cardView.peek().currentSide) {
                    CardView.CardSide.BACK -> cardView.peek().showFront()
                    CardView.CardSide.FRONT -> cardView.peek().showBack()
                }
            }
        }
    }

    //END TURN BUTTON
    private val endTurnButton = Button(
        width = 140, height = 35,
        text = "End turn"
    ).apply {
        visual = ColorVisual(242,204,143)
        this.isDisabled = playerHasNotTookAction
        onMouseClicked = {
            rootService.gameService.endTurn()
        }
    }

    /*---------------------------------------------------------*/

    /*--Labels to inform about the current state of the game--*/

    // label for the current player's name
    private val currentPlayerName = Label( font = titlesFont )
    private val nextPlayerLabel = Label(
        text = "Next player:",
        font = titlesFont)

    // label for the next player's name
    private val nextPlayerName = Label( font = playersNameFont, width = 1000 )
    private val knockedPlayerLabel = Label(
        text = "Knocked player:",
        font = titlesFont,
        isWrapText = true,
        width = 585
    )
    private val knockedPlayerName = Label(text = "-", font = playersNameFont)

    /*---------------------------------------------------------*/

    override fun refreshAfterStartNewGame() {
        val playersHandCard = rootService.gameService.currentPlayer?.handCards
        val game = rootService.currentGame
        checkNotNull(game){"No started game found"}
        checkNotNull(playersHandCard){"No started game found"}

        val cardImageLoader = CardImageLoader()

        showCardBack(playersHandCard,cardImageLoader,playersCardLabeledStackView)
        showCardFront(game.middle,cardImageLoader,middleStackLabeledStackView)

        currentPlayerName.text = "It's ${game.players[0].name} turn"
        nextPlayerName.text = game.players[1].name

        endTurnButton.isDisabled = true
    }


    override fun refreshAfterPlayerAction() {
        val playersHandCard = rootService.gameService.currentPlayer?.handCards
        val game = rootService.currentGame
        checkNotNull(game){"No started game found"}
        checkNotNull(playersHandCard){"No started game found"}

        val cardImageLoader = CardImageLoader()

        showCardFront(playersHandCard,cardImageLoader,playersCardLabeledStackView)
        showCardFront(game.middle,cardImageLoader,middleStackLabeledStackView)

        knockButton.isDisabled = hasSomeOneKnocked
    }

    override fun refreshAfterEndTurn() {
        val playersHandCard = rootService.gameService.currentPlayer?.handCards
        val game = rootService.currentGame
        checkNotNull(playersHandCard){"No started game found"}
        checkNotNull(game) {"No started game found"}

        val cardImageLoader = CardImageLoader()

        showCardBack(playersHandCard,cardImageLoader,playersCardLabeledStackView)

        currentPlayerName.text = "It's " + game.players[rootService.gameService.currentPlayerIndex].name + " turn"
        nextPlayerName.text = game.players[
                (rootService.gameService.currentPlayerIndex+1) % game.players.size].name

        mainGrid.setRowCenterMode(0,Alignment.CENTER)
        mainGrid.setRowCenterMode(2,Alignment.CENTER)


        knockButton.isDisabled = hasSomeOneKnocked
        passButton.isDisabled = false
        swapAllCardButton.isDisabled = false
        swapOneCardButton.isDisabled = false
        endTurnButton.isDisabled = true
    }

    /*------------------------------------HELP FUNCTION------------------------------*/

    private fun showCardBack(
        cardStack: MutableList<PlayCard>,
        cardImageLoader: CardImageLoader,
        stackView: List<LabeledStackView>
    ){
        for(i in 0..2){
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(
                    cardImageLoader.frontImageFor(cardStack[i].suitEnum,
                        cardStack[i].valueEnum)
                ),
                back = ImageVisual(cardImageLoader.backImage)
            )
            cardView.showBack()
            stackView[i].clear()
            stackView[i].add(cardView)
        }
    }

    private fun showCardFront(
        cardStack: MutableList<PlayCard>,
        cardImageLoader: CardImageLoader,
        stackView: List<LabeledStackView>
    ) {
        for(i in 0..2){
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(
                    cardImageLoader.frontImageFor(cardStack[i].suitEnum,
                        cardStack[i].valueEnum)
                ),
                back = ImageVisual(cardImageLoader.backImage)
            )
            cardView.showFront()
            stackView[i].clear()
            stackView[i].add(cardView)
        }
    }
    // highlight the selected card
    private fun onSelectedPlayersCards(column: Int, row: Int, cardSide: String){
        if(cardSide == "middle") {
            mainGrid.setRowCenterMode(0,Alignment.CENTER)
            mainGrid.setCellCenterMode(column, row, Alignment.TOP_CENTER)
        }else{
            mainGrid.setRowCenterMode(2,Alignment.CENTER)
            mainGrid.setCellCenterMode(column, row, Alignment.TOP_CENTER)
        }
    }

    /**
     * TODO reset knockButton and knockedPlayerName to original setup
     *
     */
    fun resetGameScene() {
        hasSomeOneKnocked = false
        knockedPlayerName.text = "-"

        knockButton.isDisabled = false
    }
    /*--------------------------------------------------------------------------------*/

    init {

        mainGrid[0, 0] = drawStack
        for (i in 1..3) {
            mainGrid[i, 0] = middleStackLabeledStackView[i - 1]
            mainGrid[i, 2] = playersCardLabeledStackView[i - 1]
        }

        buttonGrid[0, 0] = knockButton
        buttonGrid[0, 1] = passButton
        buttonGrid[0, 2] = swapOneCardButton
        buttonGrid[0, 3] = swapAllCardButton
        mainGrid[0, 2] = buttonGrid
        for (i in 0..3) {
            buttonGrid.setRowHeight(i, 50)
        }

        mainGrid[2, 1] = currentPlayerName

        nextPlayerGrid[0, 0] = nextPlayerLabel
        nextPlayerGrid[0, 1] = nextPlayerName
        nextPlayerGrid[0, 3] = knockedPlayerLabel
        nextPlayerGrid[0, 4] = knockedPlayerName
        nextPlayerGrid.setRowHeight(2,100)
        nextPlayerGrid.setColumnWidth(0,585)
        mainGrid[4, 0] = nextPlayerGrid

        rightSideButtonGrid[0, 0] = flipCardButton
        rightSideButtonGrid[0, 1] = endTurnButton
        rightSideButtonGrid.setRowHeight(0,100)
        rightSideButtonGrid.setRowHeight(1,100)
        mainGrid[4, 2] = rightSideButtonGrid

        mainGrid.setColumnWidth(0, 450)
        mainGrid.setColumnWidth(1, 340)
        mainGrid.setColumnWidth(2, 340)
        mainGrid.setColumnWidth(3, 340)
        mainGrid.setColumnWidth(4, 450)

        mainGrid.setRowHeight(0, 360)
        mainGrid.setRowHeight(1, 300)
        mainGrid.setRowHeight(2, 360)

        mainGrid.setColumnCenterMode(1, Alignment.CENTER_RIGHT)
        mainGrid.setColumnCenterMode(3, Alignment.CENTER_LEFT)

        background = ColorVisual(129, 178, 154)

        addComponents(mainGrid)
    }

}