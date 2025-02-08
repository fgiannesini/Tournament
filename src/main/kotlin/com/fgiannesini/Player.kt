package com.fgiannesini

import kotlinx.serialization.Serializable

@Serializable
data class Player(val id: Long, val pseudo: String)
