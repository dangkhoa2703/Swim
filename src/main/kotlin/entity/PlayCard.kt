package entity

/**
 * TODO
 *
 * @property suitEnum
 * @property valueEnum
 */

class PlayCard(
    val suitEnum: CardSuit,
    val valueEnum: CardValue
    ) {

    val suit: String
        get() = suitEnum.toString()

    val value: Double = when (valueEnum) {
        CardValue.SEVEN -> 7.0
        CardValue.EIGHT -> 8.0
        CardValue.NINE -> 9.0
        CardValue.TEN -> 10.0
        CardValue.JACK -> 10.0
        CardValue.QUEEN -> 10.0
        CardValue.KING -> 10.0
        CardValue.ACE -> 11.0
        }
    }
