package com.musinsa.codymaker

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.TimeZone

@SpringBootApplication
class CodyMakerApplication {

    @PostConstruct
    fun initTime() = TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
}

fun main(args: Array<String>) {
    runApplication<CodyMakerApplication>(*args)
}
