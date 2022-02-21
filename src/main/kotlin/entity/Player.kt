package entity


/**
 * TODO player's constructor
 *
 * @property name player's name
 * @property handCards player's cards
 */
class Player(
    val name: String,
    var handCards: MutableList<PlayCard>
    ) {
    init{
        require(name.length in 1..15){"invalid player's name"}
    }
    var score: Double = 0.0
    var hasKnock = false
}