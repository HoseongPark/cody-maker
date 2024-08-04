package com.musinsa.codymaker.common.exception

import com.musinsa.codymaker.util.datetime.getUtcNow
import java.time.LocalDateTime

data class ApiErrorRes(
    val message: String?,
    val time: LocalDateTime = getUtcNow()
)
