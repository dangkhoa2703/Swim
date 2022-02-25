package service

import entity.*

/**
 * TODO manage all the service, which the game provided
 *
 * @property rootService,through which this service can connect to entity layer
 */
class GameService(private val rootService: RootService): AbstractRefreshingService() {

    /**
     * count the consecutive pass among the players
     */
    var passCounter = 0
    var currentPlayerIndex = 0
    var currentPlayer: Player? = null

    /**
     * TODO create new game
     *
     * @param names list of Players' names
     */
    fun startNewGame(names: MutableList<String>){
        require(names.size in 2..4){
            "INVALID TOTAL PLAYERS!!!"
        }
        val drawStack = createDrawStack()
        val middle =  createStackOf3(drawStack)
        val tempPlayer: MutableList<Player> = mutableListOf()
        for(name in names){
            tempPlayer.add(Player(name,createStackOf3(drawStack)))
        }
        val game = Swim(names, middle, drawStack,tempPlayer.toMutableList())
        rootService.currentGame = game
        val players = rootService.currentGame?.players
        checkNotNull(players)
        currentPlayer = players[currentPlayerIndex]

        onAllRefreshables { refreshAfterStartNewGame() }
    }

    /**
     * TODO calculate the point of a player
     *
     * @param player
     * @return point(Double)
     */
    fun evaluatePlayCards(player: Player): Double{
        val card1 = player.handCards[0]
        val card2 = player.handCards[1]
        val card3 = player.handCards[2]
        for(i in 0..2){
            println("suit: ${player.handCards[i].suit} value:${player.handCards[i].value}")
        }
        var temp = card1.value
        // three cards have the same suit
        if(compareTwoCards(card1,card2) == 2 && compareTwoCards(card1,card3) == 2){
            return card1.value + card2.value + card3.value
        }
        // three cards have the same value
        else if(compareTwoCards(card1,card2) == 1 && compareTwoCards(card1,card3) == 1){
            return 30.5
        }
        // two of three cards have the same suit
        else if(compareTwoCards(card1,card2) == 2) { return card1.value + card2.value }
        else if(compareTwoCards(card1,card3) == 2) { return card1.value + card3.value }
        else if(compareTwoCards(card2,card3) == 2) { return card2.value + card3.value }
        // all 3 cards are different to each other
        else{
            for(i in 1..2){
                if(temp < player.handCards[i].value){
                    temp = player.handCards[i].value
                }
            }
            return temp
        }
    }


    /**
     * TODO reset pass counter to 0
     *
     */
    fun resetPassCounter(){
        passCounter = 0
    }

    /**
     * TODO increase pass counter by 1
     *
     */
    fun increasePassCounter(){
        passCounter++
    }

    /**
     * TODO set next player. In case of knock, check if the last round has been carried out than end the game
     *
     */
    fun endTurn(){
        val players = rootService.currentGame?.players
        checkNotNull(players)
        val nextPlayerIndex = (currentPlayerIndex+1) % players.size
        if(players[nextPlayerIndex].hasKnock){
            endGame()
        }else {
            currentPlayer = players[nextPlayerIndex]
            currentPlayerIndex = nextPlayerIndex
        }
        onAllRefreshables { refreshAfterEndTurn() }
    }

    /**
     * TODO calculate the score of each player
     *
     * @return list of increasing order score list of players
     */
    fun endGame(): List<Player>{
        val players = rootService.currentGame?.players
        checkNotNull(players)
        for(player in players){
            player.score = evaluatePlayCards(player)
        }
        onAllRefreshables { refreshAfterEndGame(players.toList().sortedByDescending{ player -> player.score }) }
        return players.toList().sortedByDescending{ player -> player.score }
    }


    /*--------------------------HELP FUNCTION-----------------------*/

    /**
     * TODO compare two cards if they have the same suit or value or both
     *
     * @param card1
     * @param card2
     * @return 0 if 2 cards are different; 1 if same value; 2 if same suit; 3 if same suit and same value
     */
    fun compareTwoCards (card1: PlayCard, card2: PlayCard): Int{
        return if(card1.suit == card2.suit && card1.valueEnum.toString() != card2.valueEnum.toString()){
            2
        }else if(card1.valueEnum.toString() == card2.valueEnum.toString() && card1.suit != card2.suit){
            1
        }else if(card1.valueEnum.toString() == card2.valueEnum.toString() && card1.suit == card2.suit){
            3
        }else{
            0
        }
    }

    /**
     * TODO create a stack of 32 cards according the rule
     *
     * @return a mutable list of 32 PlayCard object
     */
    fun createDrawStack(): MutableList<PlayCard> {
        val drawStack: MutableList<PlayCard> = mutableListOf()
        val cardValue = mutableListOf(
            CardValue.SEVEN,
            CardValue.EIGHT,
            CardValue.NINE,
            CardValue.TEN,
            CardValue.JACK,
            CardValue.QUEEN,
            CardValue.KING,
            CardValue.ACE)
        for (suitValue in CardSuit.values()) {
            for (valueValue in cardValue) {
                drawStack.add(PlayCard(suitValue, valueValue))
            }
        }
        return drawStack.shuffled() as MutableList<PlayCard>
    }

    /**
     * TODO deal 3 card from the draw stack to one player or the middle stack
     *
     * @return a mutable list of 3 PlayCard
     */
    fun deal3Card(): MutableList<PlayCard> {
        val drawStack: MutableList<PlayCard>? = rootService.currentGame?.drawStack
        checkNotNull(drawStack)
        require(drawStack.size >= 3){
            "no more cards in draw stack"
        }
        val temp: MutableList<PlayCard> = mutableListOf()
        for(i in 0..2){
            temp.add(i,drawStack[0])
            drawStack.removeAt(0)
        }
        return temp
    }

    /**
     * TODO draw 3 cards from the draw stack for one player or for the middle stack
     *
     * @param drawStack: draw from this stack
     * @return a mutable list of 3 Playcard
     */
    private fun createStackOf3(drawStack: MutableList<PlayCard>): MutableList<PlayCard> {
        val temp: MutableList<PlayCard> = mutableListOf()
        for(i in 0..2){
            temp.add(i,drawStack[0])
            drawStack.removeAt(0)
        }
        return temp
    }

}