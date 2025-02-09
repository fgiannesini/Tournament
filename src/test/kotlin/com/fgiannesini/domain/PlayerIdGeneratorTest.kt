package com.fgiannesini.domain

import org.junit.jupiter.api.Assertions.fail
import java.util.*
import kotlin.test.Test
import kotlin.test.assertNotNull

class PlayerIdGeneratorTest {

    @Test
    fun `Should generate an UUID`() {
        try {
            val newId = UUID.fromString(PlayerIdGenerator().new())
            assertNotNull(newId)
        } catch (e: IllegalArgumentException) {
            fail(e)
        }

    }
}