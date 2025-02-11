package com.fgiannesini.domain

data class Player(val id: String, val pseudo: String, val score: Int)

val NOT_FOUND = Player("Not Found", "Not Found", -1)
