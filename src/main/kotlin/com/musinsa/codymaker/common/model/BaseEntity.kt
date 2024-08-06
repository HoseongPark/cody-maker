package com.musinsa.codymaker.common.model

import com.musinsa.codymaker.common.utils.getUtcNow
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    /**
     * 식별값(ID)의 경우 자동 생성 전략을 사용하고 있으며, 기본키로 사용된다.
     *
     * 기본키를 DB에 위임함으로써 ID 생성에 대한 책임 및 관리를 DB에 맡길 수 있다.
     *
     * DB에 위임함으로써 어플리케이션 단계에서는 ID 생성에 대한 부담을 덜 수 있다는 장점이 있지만,
     * - DB에 의존적인 구조가 되기 때문에 DB를 변경해야 하는 경우에 어플리케이션에도 영향을 미칠 수 있다.
     * - 도메인 객체 생성 시점에 ID 값을 알 수 없으며, 저장 이후 조회 시점에서 해당 ID 값을 알 수있다는 단점도 존재 한다.
     *
     * 하지만 요구사항의 단순함 및 개발 생산성을 위해 ID 생성 전략을 DB에 위임하는 것으로 결정하여 처리 한다.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null

    @CreatedDate
    val created: LocalDateTime = getUtcNow()

    @LastModifiedDate
    var updated: LocalDateTime = getUtcNow()
}