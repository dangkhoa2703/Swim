package entity

import kotlin.test.*

class PlayerTest {
    private val player1 = Player("Player1")

    @Test
    fun ValidNameTest(){
        assertTrue(player1.name.length in 1..30)
    }

    @Test
    fun CheckPlayerConstructor(){
        assertEquals(player1.name , "Player1")
    }
}