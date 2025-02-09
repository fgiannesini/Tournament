package com.fgiannesini.domain

data class Player(val id: String, val pseudo: String)

const val NO_ID = "Not Found"
val NOT_FOUND = Player(NO_ID, "Not Found")
