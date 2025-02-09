package com.fgiannesini.domain

import java.util.*

class PlayerIdGenerator {

    fun new(): String = UUID.randomUUID().toString()

}
