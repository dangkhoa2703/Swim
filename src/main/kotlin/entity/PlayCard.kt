package entity

class PlayCard(
    val suit: CardSuit,
    val value: CardValue,

    ) {
    val pointInit: Int = when (value) {
        CardValue.JACK -> 10
        CardValue.QUEEN -> 10
        CardValue.KING -> 10
        CardValue.ACE -> 11
        else -> CardValue.toString().toInt()
        }
    }
