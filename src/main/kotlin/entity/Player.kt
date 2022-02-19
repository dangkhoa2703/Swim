package entity

class Player(
    val name: String,
    var handCards: MutableList<PlayCard>
    ) {
    var score: Double = 0.0
    var hasKnock = false
}