package com.fgiannesini

import kotlinx.serialization.Serializable

@Serializable
data class PlayerInformation(val id: Long, val pseudo: String)
