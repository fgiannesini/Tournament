package com.fgiannesini.domain

data class Player(val id: String, val pseudo: String, val score: Int)

const val NO_ID = "Not Found"
val NOT_FOUND = Player(NO_ID, "Not Found", -1)
