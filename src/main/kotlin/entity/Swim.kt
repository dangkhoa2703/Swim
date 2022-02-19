package entity

/**
 * TODO manage all the entity layer
 *
 * @constructor
 * TODO build a draw stack, a middle stack and create a list of player
 *
 * @param names player list
 */

data class Swim(
    val names: MutableList<String>,
    var middle: MutableList<PlayCard>,
    var drawStack: MutableList<PlayCard>,
    val players: MutableList<Player>) {
}
