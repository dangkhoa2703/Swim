package entity

/**
 * TODO play cards instructor
 *
 * @property suitEnum card's suit( type: CardSuit)
 * @property valueEnum card's value( type: CardValue )
 */

class PlayCard(
    val suitEnum: CardSuit,
    val valueEnum: CardValue
    )
{
    /**
     * suit of the card( type: String )
     */
    val suit: String
        get() = suitEnum.toString()

    /**
     * value of the card( type: Double )
     */
    val value: Double = when (valueEnum) {
        CardValue.TWO -> 2.0
        CardValue.THREE -> 3.0
        CardValue.FOUR -> 4.0
        CardValue.FIVE -> 5.0
        CardValue.SIX -> 6.0
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
