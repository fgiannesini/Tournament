package com.fgiannesini.rest

import com.fgiannesini.domain.NO_ID
import com.fgiannesini.domain.Player
import kotlinx.serialization.Serializable

@Serializable
data class PlayerCreation(val pseudo : String) {
    fun toPlayer(): Player {
        return Player(NO_ID, pseudo)
    }
}