package entity

import kotlin.random.Random

/**
 * TODO create and mandage the draw stack
 *
 * @property random: can be provided to ensure deterministic behavior fo [shuffle]
 */
class DrawStack(private val random: Random = Random) {


    /**
     * create the draw stack with 32 card
     */
      val drawStack: MutableList<PlayCard> = MutableList(32) { index ->
          PlayCard(
              CardSuit.values()[index / 8],
              CardValue.values()[(index % 8) + 5]
          )
      }.shuffled() as MutableList<PlayCard>

    /**
     * the amount of cards in this stack
     */
    val size: Int get() = drawStack.size

    /**
     * Returns `true` if the stack is empty, `false` otherwise.
     */
    val empty: Boolean get() = drawStack.isEmpty()

    /**
     * Shuffles the cards in this stack
     */
    fun shuffle() {
        drawStack.shuffle(random)
    }

    /**
     * Draws 3 cards from this stack.
     */
    fun draw(): MutableList<PlayCard> {
        val temp = MutableList(3) {index ->  drawStack[index]}
        for(i in 0..2){
            drawStack.removeAt(i)
        }
        return temp
    }
}