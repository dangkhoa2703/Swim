package entity

/**
 * TODO
 *
 * @property middleStack
 */

class Middle(val middleStack: MutableList<PlayCard>) {
    init {
        if (middleStack.size != 3) {
            throw IllegalArgumentException("wrong number of players or cadstack size")
        }
    }

}
