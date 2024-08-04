package com.musinsa.codymaker.common.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun initOpenAPI(): OpenAPI {
        val openApi = OpenAPI()
            .info(
                Info()
                    .title("CodyMaker API Documentation")
                    .description("Private CodyMaker API")
                    .version("v1.0")
                    .contact(
                        Contact()
                            .name("hoseong")
                            .email("ghtjd5689@gmail.com")
                    )
            )

        return openApi
    }
}