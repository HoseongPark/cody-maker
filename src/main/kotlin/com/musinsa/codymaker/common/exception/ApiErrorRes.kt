package com.musinsa.codymaker.common.exception

import com.musinsa.codymaker.util.datetime.getUtcNow
import java.time.LocalDateTime

data class ApiErrorRes(
    val message: String?,
    val details: List<String>? = null,
    val time: LocalDateTime = getUtcNow()
)
