package com.musinsa.codymaker.util.datetime

import java.time.LocalDateTime
import java.time.ZoneId

fun getUtcNow(): LocalDateTime {
    return LocalDateTime.now(ZoneId.of("UTC"))
}