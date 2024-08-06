package com.musinsa.codymaker.common.utils

import java.time.LocalDateTime
import java.time.ZoneId

fun getUtcNow(): LocalDateTime {
    return LocalDateTime.now(ZoneId.of("UTC"))
}